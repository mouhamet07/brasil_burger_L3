using brasilBurger.Data;
using brasilBurger.Models;
using Microsoft.EntityFrameworkCore;

namespace brasilBurger.Services.Impl
{
    public class CommandeServices : ICommandeServices
    {
        public readonly AppDbContext _context;
        private const int pageSize = 4;
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
        public List<Commande> GetCommandesByClient(int clientId,int page=1,string etat = "all")
        {
            if (page < 1) page = 1;
            int offset = (page - 1) * pageSize;
            var commandes = _context.Commandes.Where(c => c.ClientId == clientId);
            if(etat != "all"){
                var etatCm = etat=="EN_COURS" ? EtatCommande.EN_COURS :  etat=="EN_ATTENTE" ? EtatCommande.EN_ATTENTE :  etat=="ANNULEE" ? EtatCommande.ANNULEE : EtatCommande.TERMINEE;
                commandes = commandes.Where(c => c.Etat== etatCm);
            }
            return commandes
                .Include(c => c.CommandeItems)
                .Include(c => c.Client)
                .Include(c => c.Zone)
                .Include(c => c.Livreur)
                .Include(c => c.PaiementC)
                .OrderByDescending(c => c.Id)
                .Skip(offset)
                .Take(pageSize)
                .ToList();
        }
        public int CountTotal(int clientId, string etat = "all")
        {
            var query = _context.Commandes.Where(c => c.ClientId == clientId).ToList();
            if(etat != "all")
            {
                var etatC = etat=="EN_COURS" ? EtatCommande.EN_COURS :  etat=="EN_ATTENTE" ? EtatCommande.EN_ATTENTE :  etat=="ANNULEE" ? EtatCommande.ANNULEE : EtatCommande.TERMINEE;
                query = query.Where(c => c.Etat == etatC).ToList();
            }
            return query.Count();
        }
        public Commande GetCommandeById(int id)
        {
            return _context.Commandes
                .Include(c => c.CommandeItems)
                .Include(c => c.Client)
                .Include(c => c.Zone)
                .Include(c => c.Livreur)
                .Include(c => c.PaiementC)
                .FirstOrDefault(c => c.Id == id);
        }
    }
}