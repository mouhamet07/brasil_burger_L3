using brasilBurger.Models;

namespace brasilBurger.Services
{
    public interface IUserServices
    {
        User getClientById(int id);
        User Authenticate(LoginVM model);
        void CreateClient(User user);
        bool VerifyUniqueEmail(RegisterVM user);
    }
}