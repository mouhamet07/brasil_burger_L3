package sn.brasilburger;

import java.util.List;
import java.util.Optional;

import sn.brasilburger.config.factory.EntityName;
import sn.brasilburger.config.factory.services.ServicesFactory;
import sn.brasilburger.entity.*;
import sn.brasilburger.services.*;
import sn.brasilburger.views.GesViews;

public class Main {
    private boolean success;
    private BurgerService bs = (BurgerService)ServicesFactory.createServices(EntityName.BURGER);
    private ComplementService cs = (ComplementService)ServicesFactory.createServices(EntityName.COMPLEMENT);
    private LivreurService ls = (LivreurService)ServicesFactory.createServices(EntityName.LIVREUR);
    private LoginService logs = (LoginService)ServicesFactory.createServices(EntityName.LOGIN);
    private MenuService ms = (MenuService)ServicesFactory.createServices(EntityName.MENU);
    private ZoneService zs = (ZoneService)ServicesFactory.createServices(EntityName.ZONE);
    private MenuComplementService mcs = (MenuComplementService)ServicesFactory.createServices(EntityName.MENU_COMPLEMENT);
    private ImageUploadService ius = (ImageUploadService)ServicesFactory.createServices(EntityName.IMAGE_UPLOAD);

    public static void main(String[] args) {
        Main app = new Main();
        app.gesAuth();
    }
    private void gesApp(){
        int choix;
        do{
            choix = GesViews.menuPrincipale();
            switch (choix) {
                case 1:
                    gesAjout();
                    break;
                case 2:
                    gesUpdate();
                    break;
                case 3:
                    gesArchive();
                    break;
                case 4:
                    gesListe();
                    break;
                case 5:
                    System.out.println("Aurevoir");
                    return;
                default:
                    System.out.println("Choix indisponible");
                    break;
            }
        }while(choix!=5);
    }
    private void gesAjout(){
        int choix;
        do{
            choix = GesViews.menuAjout();
            switch (choix) {
                case 1:
                    System.out.println("=== Ajout d'un burger ===");
                    Burger b = new Burger();
                    b.setNom(GesViews.saisirString("Saisir le nom du burger: "));
                    b.setPrix(GesViews.saisirDouble("Saisir le prix du burger: "));
                    var imgBurger = GesViews.saisirString("Saisir le chemin de l'image");
                    try {
                        b.setImage(ius.uploadImage(imgBurger));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    success = bs.createBurger(b);
                    if (success) {
                        System.out.println("Burger ajouté avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'ajout du burger.");
                    }
                    break;
                case 2:
                    System.out.println("=== Ajout d'un Complement ===");
                    Complement c = new Complement();
                    c.setNom(GesViews.saisirString("Saisir le nom du complement: "));
                    c.setPrix(GesViews.saisirDouble("Saisir le prix du complement: "));
                    var imgCmpl = GesViews.saisirString("Saisir le chemin de l'image");
                    try {
                        c.setImage(ius.uploadImage(imgCmpl));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int choixCat;
                    do {
                        System.out.println("Choix de la categorie");
                        System.out.println("1. Frites");
                        System.out.println("2. Boisson");
                        choixCat = GesViews.saisirInt("Faites votre choix: ");
                        if (choixCat == 1) {
                            c.setCategorie(CategorieComplement.FRITES);
                        } else if (choixCat == 2) {
                            c.setCategorie(CategorieComplement.BOISSON);
                        } else {
                            System.out.println("Choix Incorrect");
                        }
                    } while (choixCat!=1 && choixCat!=2);
                    success = cs.createComplement(c);
                    if (success) {
                        System.out.println("Complement ajouté avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'ajout du Complement.");
                    }
                    break;
                case 3:
                    System.out.println("=== Ajout d'un menu ===");
                    Menu m = new Menu();
                    m.setNom(GesViews.saisirString("Saisir le nom du menu: "));
                    var imgMenu = GesViews.saisirString("Saisir le chemin de l'image");
                    try {
                        m.setImage(ius.uploadImage(imgMenu));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String s;
                    var montant = m.getMontant();
                    do{
                        List<Complement> cmpl = cs.getAllComplement();
                        GesViews.afficher(cmpl,"Aucun complement trouvé");
                        int id = GesViews.saisirInt("Saisir l'id du complement:");
                        Optional<Complement> cm = cs.getComplementById(id);
                        if(cm.isPresent()){
                            m.getComplements().add(cm.get());
                            montant += cm.get().getPrix();
                            m.setMontant(montant);
                        }
                        do{
                            s = GesViews.saisirString("Voulez vous saisir un autre complement?[O/N]").toUpperCase();
                        }while(!s.equals("N") && !s.equals("O"));
                    }while(!s.equals("N"));
                    success = ms.createMenu(m);
                    if(success){
                        for (Complement cpl : m.getComplements()) {
                            MenuComplement menuC = new MenuComplement();
                            menuC.setComplement(cpl);
                            menuC.setMenu(m);
                            mcs.createMenuComplement(menuC);
                        }
                        System.out.println("Menu ajouté avec succès !");
                    }else{
                        System.out.println("Erreur lors de l'ajout du menu.");
                    }
                    break;
                case 4:
                    System.out.println("=== Ajout d'une zone ===");
                    Zone z = new Zone();
                    z.setNom(GesViews.saisirString("Saisir le nom de la zone: "));
                    z.setPrixLivraison(GesViews.saisirDouble("Saisir le prix de livraison: "));
                    success = zs.createZone(z);
                    if (success) {
                        System.out.println("Zone ajoutée avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'ajout du zone.");
                    }
                    break;
                case 5:
                    System.out.println("=== Ajout d'un livreur ===");
                    Livreur l = new Livreur();
                    l.setNomComplet(GesViews.saisirString("Saisir le nom et prenom du livreur: "));
                    l.setTelephone(GesViews.saisirTelephone("Saisir le telephone du livreur: "));
                    List<Zone> zones = zs.getAllZone();
                    GesViews.afficher(zones,"Aucun zone trouvé");
                    Optional<Zone> zone;
                    do {
                        zone = zs.getZoneById(GesViews.saisirInt("Saisir l'id de la zone:"));
                    } while (zone.isEmpty());
                    l.setZone(zone.get());
                    success = ls.createLivreur(l);
                    if (success) {
                        System.out.println("Livreur ajouté avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'ajout du livreur.");
                    }
                    break;
                case 6:
                    System.out.println("Retour au menu principale...");
                    break;
                default:
                    System.out.println("Choix indisponible");
                    break;
            }
        }while(choix!=6);
    }
    private void gesUpdate(){
        int choix;
        String prixStr;
        String nom;
        String img;
        do{
            choix = GesViews.menuUpdate();
            switch (choix) {
                case 1:
                    int idBurger = GesViews.saisirInt("Saisir l'id du burger: ");
                    Optional<Burger> burgerOpt = bs.getBurgerById(idBurger);
                    if (burgerOpt.isEmpty()) {
                        System.out.println("Aucun burger trouve");
                        break;
                    }
                    Burger burger = burgerOpt.get();
                    nom = GesViews.saisirString(
                        "Nom actuel: " + burger.getNom() + ". Nouveau nom (laisser vide pour garder): "
                    );
                    if (!nom.isBlank()) burger.setNom(nom);
                    prixStr = GesViews.saisirString(
                        "Montant actuel: " + burger.getPrix() + ". Nouveau montant (laisser vide pour garder): "
                    );
                    if (!prixStr.isBlank()) {
                        try {
                            burger.setPrix(Double.parseDouble(prixStr));
                        } catch (NumberFormatException e) {
                            System.out.println("Montant invalide, valeur inchangée.");
                        }
                    }
                    img = GesViews.saisirString(
                        "Image actuelle: " + burger.getImage() + ". Nouveau chemin (laisser vide pour garder): "
                    );
                    if (!img.isBlank()){
                        try {
                            burger.setImage(ius.uploadImage(img));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } 
                    success = bs.updateBurger(burger);
                    if (success) {
                        System.out.println("Burger modifié avec succès !");
                    } else {
                        System.out.println("Erreur lors de la modification du burger.");
                    }
                    break;
                case 2:
                    int idComplement = GesViews.saisirInt("Saisir l'id du complement: ");
                    Optional<Complement> compOpt = cs.getComplementById(idComplement);
                    if (compOpt.isEmpty()) {
                        System.out.println("Aucun complement trouvé");
                        break;
                    }
                    Complement comp = compOpt.get();
                    nom = GesViews.saisirString(
                        "Nom actuel: " + comp.getNom() + ". Nouveau nom (laisser vide pour garder): "
                    );
                    if (!nom.isBlank()) comp.setNom(nom);
                    prixStr = GesViews.saisirString(
                        "Montant actuel: " + comp.getPrix() + ". Nouveau montant (laisser vide pour garder): "
                    );
                    if (!prixStr.isBlank()) {
                        try {
                            comp.setPrix(Double.parseDouble(prixStr));
                        } catch (NumberFormatException e) {
                            System.out.println("Montant invalide, valeur inchangée.");
                        }
                    }
                    img = GesViews.saisirString(
                        "Image actuelle: " + comp.getImage() + ". Nouveau chemin (laisser vide pour garder): "
                    );
                    if (!img.isBlank()){
                        try {
                            comp.setImage(ius.uploadImage(img));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } 
                    success = cs.updateComplement(comp);
                    if (success) {
                        System.out.println("Complement modifié avec succès !");
                    } else {
                        System.out.println("Erreur lors de la modification du complement.");
                    }
                    break;
                case 3:
                    int idMenu = GesViews.saisirInt("Saisir l'id du menu: ");
                    Optional<Menu> menuOpt = ms.getMenuById(idMenu);
                    if (menuOpt.isEmpty()) {
                        System.out.println("Aucun menu trouvé");
                        break;
                    }
                    Menu menu = menuOpt.get();
                    nom = GesViews.saisirString(
                        "Nom actuel: " + menu.getNom() + ". Nouveau nom (laisser vide pour garder): "
                    );
                    if (!nom.isBlank()) menu.setNom(nom);
                    img = GesViews.saisirString(
                        "Image actuelle: " + menu.getImage() + ". Nouveau chemin (laisser vide pour garder): "
                    );
                    if (!img.isBlank()){
                        try {
                            menu.setImage(ius.uploadImage(img));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } 
                    success = ms.updateMenu(menu);
                    if (success) {
                        System.out.println("Menu modifié avec succès !");
                    } else {
                        System.out.println("Erreur lors de la modification du menu.");
                    }
                    break;
                case 4:
                    int idZone = GesViews.saisirInt("Saisir l'id de la zone: ");
                    Optional<Zone> zoneOpt = zs.getZoneById(idZone);
                    if (zoneOpt.isEmpty()) {
                        System.out.println("Aucune zone trouvée");
                        break;
                    }
                    Zone zone = zoneOpt.get();
                    nom = GesViews.saisirString(
                        "Nom actuel: " + zone.getNom() + ". Nouveau nom (laisser vide pour garder): "
                    );
                    if (!nom.isBlank()) zone.setNom(nom);
                    prixStr = GesViews.saisirString(
                        "Montant actuel: " + zone.getPrixLivraison() + ". Nouveau montant (laisser vide pour garder): "
                    );
                    if (!prixStr.isBlank()) {
                        try {
                            zone.setPrixLivraison(Double.parseDouble(prixStr));
                        } catch (NumberFormatException e) {
                            System.out.println("Montant invalide, valeur inchangée.");
                        }
                    }
                    success = zs.updateZone(zone);
                    if (success) {
                        System.out.println("Zone modifiée avec succès !");
                    } else {
                        System.out.println("Erreur lors de la modification de la zone.");
                    }
                    break;
                case 5:
                    int idLivreur = GesViews.saisirInt("Saisir l'id du livreur: ");
                    Optional<Livreur> livOpt = ls.getLivreurById(idLivreur);
                    if (livOpt.isEmpty()) {
                        System.out.println("Aucun livreur trouvé");
                        break;
                    }
                    Livreur liv = livOpt.get();
                    nom = GesViews.saisirString(
                        "Nom actuel: " + liv.getNomComplet() + ". Nouveau nom (laisser vide pour garder): "
                    );
                    if (!nom.isBlank()) liv.setNomComplet(nom);
                    String tel = GesViews.saisirTelephone(
                        "Telephone actuel: " + liv.getTelephone() + ". Nouveau telephone (laisser vide pour garder): "
                    );
                    if (!tel.isBlank()) liv.setTelephone(tel);
                    List<Zone> zones = zs.getAllZone();
                    GesViews.afficher(zones, "Aucune zone trouvée");
                    String zoneInput = GesViews.saisirString(
                        "ID de la zone actuelle: " + (liv.getZone() != null ? liv.getZone().getId() : "aucune") +
                        ". Nouveau ID (laisser vide pour garder) : "
                    );
                    if (!zoneInput.isBlank()) {
                        try {
                            idZone = Integer.parseInt(zoneInput);
                            Optional<Zone> zOpt = zs.getZoneById(idZone);
                            if (zOpt.isPresent()) {
                                liv.setZone(zOpt.get());
                            } else {
                                System.out.println("Zone invalide, valeur inchangée.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("ID invalide, valeur inchangée.");
                        }
                    }
                    if (success) {
                        System.out.println("Livreur modifié avec succès !");
                    } else {
                        System.out.println("Erreur lors de la modification du livreur.");
                    }
                    break;
                case 6:
                    System.out.println("Retour au menu principale...");
                    break;
                default:
                    System.out.println("Choix indisponible");
                    break;
            }
        }while(choix!=6);
    }
    private void gesArchive(){
        int choix;
        do{
            choix = GesViews.menuArchive();
            switch (choix) {
                case 1:
                    int idBurger = GesViews.saisirInt("Saisir l'id du burger: ");
                    Optional<Burger> burger = bs.getBurgerById(idBurger);
                    if (burger.isEmpty()) {
                        System.out.println("Aucun burger trouvé");
                        break;
                    }
                    success = bs.archiveBurger(burger.get());
                    if (success) {
                        System.out.println("Burger archivé avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'archivage du burger.");
                    }
                    break;
                case 2:
                    int idComplement = GesViews.saisirInt("Saisir l'id du complement: ");
                    Optional<Complement> comp = cs.getComplementById(idComplement);
                    if (comp.isEmpty()) {
                        System.out.println("Aucun complement trouvé");
                        break;
                    }
                    success = cs.archiveComplement(comp.get());
                    if (success) {
                        System.out.println("Complement archivé avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'archivage du complement.");
                    }
                    break;
                case 3:
                    int idMenu = GesViews.saisirInt("Saisir l'id du menu: ");
                    Optional<Menu> menu = ms.getMenuById(idMenu);
                    if (menu.isEmpty()) {
                        System.out.println("Aucun menu trouvé");
                        break;
                    }
                    success = ms.archiveMenu(menu.get());
                    if (success) {
                        System.out.println("Menu archivé avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'archivage du menu.");
                    }
                    break;
                case 4:
                    int idZone = GesViews.saisirInt("Saisir l'id de la zone: ");
                    Optional<Zone> zone = zs.getZoneById(idZone);
                    if (zone.isEmpty()) {
                        System.out.println("Aucune zone trouvée");
                        break;
                    }
                    success = zs.archiveZone(zone.get());
                    if (success) {
                        System.out.println("Zone archivée avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'archivage de la zone.");
                    }
                    break;
                case 5:
                    int idLivreur = GesViews.saisirInt("Saisir l'id du livreur: ");
                    Optional<Livreur> liv = ls.getLivreurById(idLivreur);
                    if (liv.isEmpty()) {
                        System.out.println("Aucun livreur trouvé");
                        break;
                    }
                    success = ls.archiveLivreur(liv.get());
                    if (success) {
                        System.out.println("Livreur archivé avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'archivage du livreur.");
                    }
                    break;
                case 6:
                    System.out.println("Retour au menu principale...");
                    break;
                default:
                    System.out.println("Choix indisponible");
                    break;
            }
        }while(choix!=6);
    }
    private void gesListe(){
        int choix;
        do{
            choix = GesViews.menuLister();
            switch (choix) {
                case 1:
                    List<Burger> burgers = bs.getAllBurger();
                    GesViews.afficher(burgers,"Aucun burger trouvé");
                    break;
                case 2:
                    List<Complement> complements = cs.getAllComplement();
                    GesViews.afficher(complements,"Aucun complement trouvé");
                    break;
                case 3:
                    List<Menu> menus = ms.getAllMenu();
                    for (Menu menu : menus) {
                        System.out.println(menu);
                        System.out.println("=== COMPLEMENTS ===");
                        List<Complement> complementsMenu = cs.getComplementsByMenu(menu.getId());
                        menu.setComplements(complementsMenu);
                        complementsMenu.forEach(System.out::println);
                    }
                    break;
                case 4:
                    List<Zone> zones = zs.getAllZone();
                    GesViews.afficher(zones,"Aucun zone trouvé");
                    break;
                case 5:
                    List<Livreur> livreurs = ls.getAllLivreur();
                    GesViews.afficher(livreurs,"Aucun livreur trouvé");
                    break;
                case 6:
                    System.out.println("Retour au menu principale...");
                    break;
                default:
                    System.out.println("Choix indisponible");
                    break;
            }
        }while(choix!=6);
    }
    private void gesAuth(){
        int choix;
        do {
            choix = GesViews.menuAuthentification();
            switch (choix) {
                case 1:
                    User user = Login();
                    if (user != null) {
                        gesApp();
                    }
                    break;
                case 2:
                    System.out.println("Ajout d'un gestionnaire ==");
                    User newUser = new User();
                    newUser.setNomComplet(GesViews.saisirString("Nom complet: "));
                    newUser.setTelephone(GesViews.saisirTelephone("Telephone: "));
                    newUser.setEmail(GesViews.saisirString("Email: "));
                    newUser.setPassword(GesViews.saisirString("Mot de passe: "));
                    newUser.setRole(RoleUser.GESTIONNAIRE);
                    success = logs.signup(newUser);
                    if (success) {
                        System.out.println("Gestionnaire ajouté avec succès !");
                    } else {
                        System.out.println("Erreur lors de l'ajout du gestionnaire.");
                    }
                    break;
                case 3:
                    System.out.println("Aurevoir");
                    break;
                default:
                    System.out.println("Choix indisponible");
                    break;
            }
        } while (choix != 3);
    }
    private User Login(){
        System.out.println("=== Authentification ===");
        String email = GesViews.saisirString("Email: ");
        String password = GesViews.saisirString("Mot de passe: ");
        Optional<User> user = logs.login(email, password);
        if (user.isPresent() && user.get().getRole() == RoleUser.GESTIONNAIRE) {
            System.out.println("=== Authentification réussie. Bienvenue " + user.get().getNomComplet() + " ===");
            return user.get();
        } else {
            System.out.println("Échec de l'authentification. Veuillez vérifier vos identifiants.");
            return null;
        }
    }
}
