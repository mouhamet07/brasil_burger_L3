using System.ComponentModel.DataAnnotations;

namespace brasilBurger.Models
{
    public class RegisterVM
    {
        [Required]
        public string NomComplet { get; set; }
        [Required]
        [Phone]
        public string Telephone { get; set; }
        [Required]
        [EmailAddress]
        public string Email { get; set; }
        [Required]
        [MinLength(6)]
        public string Password { get; set; }
    }
}
