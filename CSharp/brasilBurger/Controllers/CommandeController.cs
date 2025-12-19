using brasilBurger.Filters;
using brasilBurger.Models;
using brasilBurger.Services;
using Microsoft.AspNetCore.Mvc;

namespace brasilBurger.Controllers
{
    [AuthRequired]
    public class CommandeController : Controller
    {
        private readonly ILogger<CommandeController> _logger;
        private readonly ICatalogueServices _catalogueServices;
        private readonly ICommandeServices _commandeServices;
        private readonly IUserServices _userServices;
        public CommandeController(ICommandeServices commandeServices,ILogger<CommandeController> logger,ICatalogueServices cata,IUserServices userServices)
        {
            _logger = logger;
            _commandeServices = commandeServices;
            _catalogueServices = cata;
            _userServices = userServices;
        }
        [HttpPost]
        public IActionResult Commander(int ProduitId,string Type,List<int> SelectedComplements,int _Quantite)
        {
            try
            {
                var quantite=_Quantite>0?_Quantite:1;
                SelectedComplements??=new List<int>();
                CatalogueItemVM item=Type=="Burger"?_catalogueServices.GetItemById(ProduitId,"burger"):_catalogueServices.GetItemById(ProduitId,"Menu");
                if(item==null)
                    return RedirectToAction("Index","Catalogue");
                var complements=_catalogueServices.GetComplements().Where(c=>SelectedComplements.Contains(c.Id)).ToList();
                var paiementVM=new PaiementVM
                {
                    ProduitId=item.Id,
                    Type=item.Type,
                    NomProduit=item.Nom,
                    PrixProduit=item.Prix,
                    Complements=complements,
                    Quantite=quantite,
                    Total=(item.Prix*quantite)+(complements.Sum(c=>c.Prix)*quantite),
                    ComplementIds=complements.Select(c=>c.Id).ToList()
                };
                ViewBag.Zones=_catalogueServices.GetZones();
                ViewBag.Client=_userServices.getClientById((int)HttpContext.Session.GetInt32("UserId"));
                return View("Paiement",paiementVM);
            }
            catch(Exception)
            {
                TempData["ErrorMessages"]="Erreur lors de la commande";
                return RedirectToAction("Index","Catalogue");
            }
        }
        [HttpGet]
        public IActionResult Index(int page=1,string etat="all")
        {
            try
            {
                var id=(int)HttpContext.Session.GetInt32("UserId");
                var commandes=_commandeServices.GetCommandesByClient(id,page,etat);
                ViewBag.CurrentPage=page;
                ViewBag.TotalPages=(int)Math.Ceiling((double)_commandeServices.CountTotal(1,etat)/4);
                ViewBag.SelectedEtat=etat;
                return View(commandes);
            }
            catch(Exception)
            {
                _logger.LogError("Erreur lors de l'affichage des commandes");
                return View(new List<Commande>());
            }
        }
        [HttpGet]
        public IActionResult Details(int id)
        {
            try
            {
                var commande=_commandeServices.GetCommandeById(id);
                if(commande==null)
                    commande=new Commande();
                return View(commande);
            }
            catch(Exception)
            {
                _logger.LogError("Erreur lors de l'affichage de la commande");
                return View(new Commande());
            }
        }
    }
}
