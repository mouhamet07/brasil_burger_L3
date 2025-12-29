using System.Security.Cryptography;
using System.Text;
using brasilBurger.Controllers;
using brasilBurger.Data;
using brasilBurger.Models;

namespace brasilBurger.Services.Impl
{
    public class UserServices : IUserServices
    {
        private readonly AppDbContext _context;
        private readonly ILogger<CatalogueController> _logger;
        public UserServices(AppDbContext context, ILogger<CatalogueController> logger)
        {
            _context = context;
            _logger = logger;
        }
        public User getClientById(int id)
        {
            try
            {
                return _context.Users
                    .Where(u => u.Etat == true)
                    .FirstOrDefault(u => u.Id == id);
            }
            catch (Exception)
            {
                _logger.LogError("Erreur lors de la recuperation du client");
                throw;
            }
        }
        public User Authenticate(LoginVM model)
        {
            var user = _context.Users
                .Where(u => u.Role == RoleUser.CLIENT)
                .Where(u => u.Etat == true)
                .FirstOrDefault(u => u.Email == model.Login || u.Telephone == model.Login);
            if (user == null || HashPassword(model.Password) != user.Password)
            {
                return null;
            }
            return user;
        }
        public void CreateClient(User user)
        {
            user.Password = HashPassword(user.Password);
            _context.Users.Add(user);
            _context.SaveChanges();
        }
        public bool VerifyUniqueEmail(RegisterVM user)
        {
            return _context.Users.Any(u => u.Email == user.Email);
        }
        private string HashPassword(string password)
        {
            using (SHA256 sha256 = SHA256.Create())
            {
                byte[] bytes = sha256.ComputeHash(Encoding.UTF8.GetBytes(password));
                StringBuilder builder = new StringBuilder();
                foreach (var b in bytes)
                {
                    builder.Append(b.ToString("x2"));
                }
                return builder.ToString();
            }
        }
    }
}
