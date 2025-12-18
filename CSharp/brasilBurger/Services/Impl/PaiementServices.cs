using brasilBurger.Data;
using brasilBurger.Models;

namespace brasilBurger.Services.Impl
{
    public class PaiementServices : IPaiementServices
    {
        public readonly AppDbContext _context;
        public PaiementServices(AppDbContext context)
        {
            _context = context;
        }
        public void CreatePaiement(Paiement paiement)
        {
            _context.Paiements.Add(paiement);
            _context.SaveChanges();
        }
    }
}