using brasilBurger.Data;
using brasilBurger.Filters;
using brasilBurger.Models;
using brasilBurger.Services;
using Microsoft.AspNetCore.Mvc;

namespace brasilBurger.Controllers
{
    [AuthRequired]
    public class PaiementController : Controller
    {
        private readonly AppDbContext _context;
        private readonly ICommandeServices _commandeServices;
        private readonly ICatalogueServices _catalogueServices;
        private readonly IPaiementServices _paiementServices;
        private ILogger<AppDbContext> _logger;
        public PaiementController(AppDbContext context,ILogger<AppDbContext> logger,ICommandeServices commandeServices,ICatalogueServices catalogueServices,IPaiementServices paiementServices,IUserServices userServices)
        {
            _context = context;
            _commandeServices = commandeServices;
            _catalogueServices = catalogueServices;
            _paiementServices = paiementServices;
            _logger = logger;
        }
        [HttpPost]
        public IActionResult Payer(PaiementVM paiementVM,string Telephone,string TypeCmd,int? ZoneId,string ModePaie)
        {
            try
            {
                var typeEnum = TypeCmd=="SUR_PLACE" ? TypeCommande.SUR_PLACE : TypeCmd=="A_RECUPERER" ? TypeCommande.A_RECUPERER : TypeCommande.LIVRAISON;
                var zoneId = TypeCmd=="LIVRAISON" ? ZoneId : null;
                decimal total = paiementVM.PrixProduit * paiementVM.Quantite;
                if(paiementVM.ComplementIds!=null)
                {
                    foreach(var compId in paiementVM.ComplementIds)
                        total+=_catalogueServices.GetComplementById(compId).Prix*paiementVM.Quantite;
                }
                if(zoneId!=null)
                    total+=_catalogueServices.GetZoneById(zoneId.Value).PrixLivraison;
                var cmd = new Commande
                {
                    DateCommande = DateTime.UtcNow,
                    Etat = TypeCmd=="LIVRAISON" ? EtatCommande.EN_ATTENTE : EtatCommande.EN_COURS,
                    Type = typeEnum,
                    ClientId = (int)HttpContext.Session.GetInt32("UserId"),
                    ZoneId = zoneId,
                    MontantTotal = total
                };
                _commandeServices.CreateCommande(cmd);
                var item = new CommandeItem
                {
                    CommandeId = cmd.Id,
                    ProduitId = paiementVM.ProduitId,
                    Type = paiementVM.Type=="Burger" ? TypeCommandeItem.BURGER : TypeCommandeItem.MENU,
                    Quantite = paiementVM.Quantite,
                    PrixUnitaire = paiementVM.PrixProduit
                };
                _commandeServices.CreateCommandeItem(item);
                if(paiementVM.ComplementIds!=null)
                {
                    foreach(var compId in paiementVM.ComplementIds)
                    {
                        var compItem=new CommandeItem
                        {
                            CommandeId=cmd.Id,
                            ProduitId=compId,
                            Type=TypeCommandeItem.COMPLEMENT,
                            Quantite=paiementVM.Quantite,
                            PrixUnitaire=_catalogueServices.GetComplementById(compId).Prix
                        };
                        _commandeServices.CreateCommandeItem(compItem);
                    }
                }
                var paiement=new Paiement
                {
                    CommandeId=cmd.Id,
                    DatePaiement=DateTime.UtcNow,
                    Montant=total,
                    Mode=ModePaie=="WAVE"?ModePaiement.WAVE:ModePaiement.OM
                };
                _paiementServices.CreatePaiement(paiement);
                TempData["SuccessMessages"]="Commande effectuée avec succès";
                return RedirectToAction("Index","Commande");
            }
            catch(Exception)
            {
                TempData["ErrorMessages"]="Erreur lors de la commande";
                return RedirectToAction("Index","Catalogue");
            }
        }
    }
}
