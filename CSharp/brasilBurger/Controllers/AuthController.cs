using brasilBurger.Data;
using brasilBurger.Filters;
using brasilBurger.Models;
using brasilBurger.Services;
using Microsoft.AspNetCore.Mvc;

namespace brasilBurger.Controllers
{
    public class AuthController : Controller
    {
        private readonly IUserServices _userServices;
        private readonly ILogger<AuthController> _logger;
        private readonly AppDbContext _context;
        public AuthController(IUserServices userServices, ILogger<AuthController> logger,AppDbContext context)
        {
            _logger = logger;
            _userServices = userServices;
            _context = context;
        }
        [HttpGet]
        public IActionResult Login()
        {
            if (HttpContext.Session.GetInt32("UserId") != null)
                return RedirectToAction("Index", "Catalogue");
            return View();
        }
        [HttpPost]
        public IActionResult Login(LoginVM model)
        {
            try
            {
                var user = _userServices.Authenticate(model);
                if(user!=null)
                    {
                    HttpContext.Session.SetInt32("UserId", user.Id);
                    HttpContext.Session.SetString("UserRole", user.Role.ToString());
                    HttpContext.Session.SetString("UserName", user.NomComplet);
                    return RedirectToAction("Index", "Catalogue");
                }
                    return RedirectToAction("Login");
            }
            catch (Exception)
            {
                _logger.LogError("Erreur lors de l'authentification");
                throw;
            }
        }
        [AuthRequired]
        public IActionResult Logout()
        {
            try
            {
                HttpContext.Session.Clear();
                return RedirectToAction("Login");
            }
            catch (Exception)
            {
                _logger.LogError("Erreur lors de la déconnexion");
                throw;
            }
        }
        [HttpGet]
        public IActionResult Register()
        {
            if (HttpContext.Session.GetInt32("UserId") != null)
                return RedirectToAction("Index", "Catalogue");
            return View();
        }

        [HttpPost]
        public IActionResult Register(RegisterVM model)
        {
            if (!ModelState.IsValid)
                return View(model);
            var emailExiste = _userServices.VerifyUniqueEmail(model);
            if (emailExiste)
            {
                _logger.LogError("Email déjà utilisé");
                return View(model);
            }
            var user = new User
            {
                NomComplet = model.NomComplet,
                Telephone = model.Telephone,
                Email = model.Email,
                Password = model.Password,
                Role = RoleUser.CLIENT,
                Etat = true
            };
            _userServices.CreateClient(user);
            HttpContext.Session.SetInt32("UserId", user.Id);
            HttpContext.Session.SetString("UserRole", user.Role.ToString());
            return RedirectToAction("Index", "Catalogue");
        }
    }
}