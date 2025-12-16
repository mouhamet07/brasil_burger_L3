using brasilBurger.Data;
using brasilBurger.Models;

namespace brasilBurger.Services.Impl
{
    public class CatalogueServices : ICatalogueServices
    {
        private readonly AppDbContext _context;
        private const int pageSize = 4;
        public CatalogueServices(AppDbContext context)
        {
            _context = context;
        }
        public List<CatalogueItemVM> GetCatalogue(int page = 1, string type = "all")
        {
            if (page < 1) page = 1;
            int offset = (page - 1) * pageSize;

            var burgers = _context.Burgers.Select(b => new CatalogueItemVM
            {
                Id = b.Id,
                Nom = b.Nom,
                Prix = b.Prix,
                Image = b.Image,
                Type = "Burger"
            });

            var menus = _context.Menus.Select(m => new CatalogueItemVM
            {
                Id = m.Id,
                Nom = m.Nom,
                Prix = m.Montant,
                Image = m.Image,
                Type = "Menu"
            });

            var allItems = burgers.Concat(menus);

            // Appliquer le filtre AVANT la pagination
            if (!string.IsNullOrEmpty(type) && type.ToLower() != "all")
                allItems = allItems.Where(c => c.Type.ToLower() == type.ToLower());

            return allItems
                .OrderBy(c => c.Id)
                .Skip(offset)
                .Take(pageSize)
                .ToList();
        }

        public int CountTotal(string type = "all")
        {
            var burgersCount = _context.Burgers.Count();
            var menusCount = _context.Menus.Count();

            type = type?.ToLower() ?? "all";

        if (type == "burger")
            return burgersCount;
        else if (type == "menu")
            return menusCount;
        else
            return burgersCount + menusCount;

        }
    }
}