using brasilBurger.Models;
using brasilBurger.Services;
using Microsoft.AspNetCore.Mvc;

namespace brasilBurger.Controllers
{
    public class CommandeController : Controller
    {
        private readonly ILogger<CommandeController> _logger;
        private readonly ICatalogueServices _catalogueServices;
        private readonly ICommandeServices _commandeServices;
        public CommandeController(ICommandeServices commandeServices, ILogger<CommandeController> logger, ICatalogueServices cata)
        {
            _logger = logger;
            _commandeServices = commandeServices;
            _catalogueServices = cata;
        }
        [HttpPost]
        public IActionResult Commander(int ProduitId, string Type, List<int> SelectedComplements, int _Quantite)
        {
            CatalogueItemVM item = null;
            if(Type == "Burger")
            {
                item = _catalogueServices.GetItemById(ProduitId, "burger");
            }else if(Type == "Menu")
            {
                item = _catalogueServices.GetItemById(ProduitId, "Menu");
            }
            var complements = _catalogueServices.GetComplements()
                .Where(c => SelectedComplements.Contains(c.Id))
                .ToList();
            var zones = _catalogueServices.GetZones();
            ViewBag.Zones = zones;
            var paiementVM = new PaiementVM
            {
                ProduitId = item.Id,
                Type = item.Type,
                NomProduit = item.Nom,
                PrixProduit = item.Prix,
                Complements = complements,
                Quantite = _Quantite<=0 ? _Quantite : 1,
                Total = (item.Prix * _Quantite) + complements.Sum(c => c.Prix),
                ComplementIds = complements.Select(c => c.Id).ToList()
            };
            if(item == null)
                return RedirectToAction("Index", "Catalogue");
            return View("Paiement",paiementVM);
        }
        [HttpGet]
        public IActionResult Index(int page=1, String etat="all")
        {
            var commandes = _commandeServices.GetCommandesByClient(1,page,etat); // A gerer
            ViewBag.CurrentPage = page;
            ViewBag.TotalPages = (int)Math.Ceiling(
                (double)_commandeServices.CountTotal(1,etat) / 4
            );
            ViewBag.SelectedEtat = etat;
            return View(commandes);
        }
        [HttpGet]
        public IActionResult Details(int id)
        {
            var commande = _commandeServices.GetCommandeById(id);
            if(commande == null) 
                commande = new Commande();
            return View(commande);
        }
    }
}