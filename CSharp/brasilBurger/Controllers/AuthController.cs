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
                    TempData["SuccessMessages"] = "Connexion effectué avec succès";
                    return RedirectToAction("Index", "Catalogue");
                }
                    TempData["ErrorMessages"] = "Login ou mot de passe invalide!";
                    return RedirectToAction("Login");
            }
            catch (Exception)
            {
                TempData["ErrorMessages"] = "Erreur lors de la connexion, réessayer!";
                return RedirectToAction("Login");
            }
        }
        [AuthRequired]
        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            return RedirectToAction("Login");
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
            try
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
                HttpContext.Session.SetString("UserName", user.NomComplet);
                TempData["SuccessMessages"] = "Inscription effectuée avec succès";
                return RedirectToAction("Index", "Catalogue");
            }
            catch (Exception)
            {
                TempData["ErrorMessages"] = "Erreur : Les informations sont invalides !";
                return RedirectToAction("Register");
            }
        }
    }
}