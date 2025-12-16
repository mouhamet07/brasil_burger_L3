using brasilBurger.Models;

namespace brasilBurger.Services
{
    public interface ICatalogueServices
    {
        List<CatalogueItemVM> GetCatalogue(int page, string type = "all");
        int CountTotal(string type = "all");
    }
}