using brasilBurger.Data;
using brasilBurger.Models;

namespace brasilBurger.Services.Impl
{
    public class CommandeServices : ICommandeServices
    {
        public readonly AppDbContext _context;
        public CommandeServices(AppDbContext context)
        {
            _context = context;
        }
        public void CreateCommande(Commande cmd)
        {
            _context.Commandes.Add(cmd);
            _context.SaveChanges();
        }
        public void CreateCommandeItem(CommandeItem cmdItem)
        {
            _context.CommandeItems.Add(cmdItem);
            _context.SaveChanges();
        }
    }
}