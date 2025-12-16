using brasilBurger.Services;
using Microsoft.AspNetCore.Mvc;

namespace brasilBurger.Controllers
{
    public class CatalogueController : Controller
    {
        private readonly ICatalogueServices _catalogueServices;
        private readonly ILogger<CatalogueController> _logger;
        public CatalogueController(ICatalogueServices catSer, ILogger<CatalogueController> logger)
        {
            _logger = logger;
            _catalogueServices = catSer;
        }
        [HttpGet]
        public IActionResult Index(int page = 1, string type = "all")
        {
            var catalogue = _catalogueServices.GetCatalogue(page, type);
            ViewBag.CurrentPage = page;
            ViewBag.TotalPages = (int)Math.Ceiling(
                (double)_catalogueServices.CountTotal(type) / 4
            );
            ViewBag.SelectedType = type;
            return View(catalogue);
        }
    }
}