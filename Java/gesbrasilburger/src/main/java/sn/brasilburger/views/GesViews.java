package sn.brasilburger.views;

import java.util.List;
import java.util.Scanner;

public class GesViews {
    private static Scanner scanner = new Scanner(System.in);
    private GesViews(){
    }
    public static int menuPrincipale(){
        System.out.println("=== Bienvenue dans Brasil Burger ===");
        System.out.println("1. Ajouter une ressource");
        System.out.println("2. Modifier une ressource");
        System.out.println("3. Archiver une ressource");
        System.out.println("4. Listes des ressources");
        System.out.println("5. Quitter");
        System.out.println("Faites votre choix: ");
        int choix = scanner.nextInt();
        scanner.nextLine();
        return choix;
    }
    public static int menuAjout(){
        System.out.println("=== Ajouter une ressource ===");
        System.out.println("1. Ajouter un Burger");
        System.out.println("2. Ajouter un Complement");
        System.out.println("3. Ajouter un Menu");
        System.out.println("4. Ajouter une Zone");
        System.out.println("5. Ajouter un Livreur");
        System.out.println("6. Retour");
        System.out.println("Faites votre choix: ");
        int choix = scanner.nextInt();
        scanner.nextLine();
        return choix;
    }
    public static int menuUpdate(){
        System.out.println("=== Modifier une ressource ===");
        System.out.println("1. Modifier un Burger");
        System.out.println("2. Modifier un Complement");
        System.out.println("3. Modifier un Menu");
        System.out.println("4. Modifier une Zone");
        System.out.println("5. Modifier un Livreur");
        System.out.println("6. Retour");
        System.out.println("Faites votre choix: ");
        int choix = scanner.nextInt();
        scanner.nextLine();
        return choix;
    }
    public static int menuArchive(){
        System.out.println("=== Archiver une ressource ===");
        System.out.println("1. Archiver un Burger");
        System.out.println("2. Archiver un Complement");
        System.out.println("3. Archiver un Menu");
        System.out.println("4. Archiver une Zone");
        System.out.println("5. Archiver un Livreur");
        System.out.println("6. Retour");
        int choix = scanner.nextInt();
        scanner.nextLine();
        return choix;
    }
    public static int menuLister(){
        System.out.println("=== Lister des ressources ===");
        System.out.println("1. Lister des Burgers");
        System.out.println("2. Lister des Complements");
        System.out.println("3. Lister des Menus");
        System.out.println("4. Lister des Zones");
        System.out.println("5. Lister des Livreurs");
        System.out.println("6. Retour");
        int choix = scanner.nextInt();
        scanner.nextLine();
        return choix;
    }
    public static String saisirString(String msg){
        String champ;
        do {
            System.out.println(msg);
            champ = scanner.nextLine();
            if (champ.isEmpty()) {
                System.out.println("Champ obligatoire!!");
            }
        } while (champ.isEmpty());
        return champ;
    }
    public static int saisirInt(String msg){
        int champ;
        do {
            System.out.println(msg);
            champ = scanner.nextInt();
            scanner.nextLine();
            if (champ < 0) {
                System.out.println("Le Champ doit etre positif!!");
            }
        } while (champ < 0);
        return champ;
    }
    public static double saisirDouble(String msg){
        double champ;
        do {
            System.out.println(msg);
            champ = scanner.nextInt();
            scanner.nextLine();
            if (champ < 0) {
                System.out.println("Le Champ doit etre positif!!");
            }
        } while (champ < 0);
        return champ;
    }
    public static String saisirTelephone(String msg){
        String champ;
        String regex = "^(77|78|76|75|70|71)[0-9]{7}$";
        do {
            System.out.println(msg);
            champ = scanner.nextLine().trim();
            if (!champ.matches(regex)) {
                System.out.println("Le telephone doit contenir 9 chiffres et commencer par 77, 78, 76, 75, 70 ou 71.");
            }
            if(champ.isEmpty()){
                System.out.println("Champ Obligatoire ");
            }
        } while (champ.isEmpty() || !champ.matches(regex));
        return champ;
    }
    public static <T> void afficher(List<T> list,String msg){
        if (list.isEmpty()) {
            System.out.println(msg);
            return;
        }
        list.forEach(System.out::println);
    }
}
