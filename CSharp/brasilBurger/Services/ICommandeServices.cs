using brasilBurger.Models;

namespace brasilBurger.Services
{
    public interface ICommandeServices
    {
        void CreateCommande(Commande cmd);
        void CreateCommandeItem(CommandeItem cmdItem);
    }
}