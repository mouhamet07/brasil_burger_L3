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
    }
}