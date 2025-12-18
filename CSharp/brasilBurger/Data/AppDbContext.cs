using brasilBurger.Models;
using Microsoft.EntityFrameworkCore;

namespace brasilBurger.Data
{
    public class AppDbContext : DbContext
    {
        public DbSet<User> Users { get; set; }
        public DbSet<Zone> Zones { get; set; }
        public DbSet<Livreur> Livreurs { get; set; }
        public DbSet<Burger> Burgers { get; set; }
        public DbSet<Menu> Menus { get; set; }
        public DbSet<Complement> Complements { get; set; }
        public DbSet<MenuComplement> MenuComplements { get; set; }
        public DbSet<Commande> Commandes { get; set; }
        public DbSet<CommandeItem> CommandeItems { get; set; }
        public DbSet<Paiement> Paiements { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            //User
            modelBuilder.Entity<User>(entity =>
            {
                entity.ToTable("user");
                entity.HasKey(u => u.Id);
                entity.Property(u => u.Id).HasColumnName("id");
                entity.Property(u => u.NomComplet).HasColumnName("nom_complet");
                entity.Property(u => u.Telephone).HasColumnName("telephone");
                entity.Property(u => u.Email).HasColumnName("email");
                entity.Property(u => u.Password).HasColumnName("password");
                entity.Property(u => u.Role).HasColumnName("role").HasConversion<string>();
                entity.Property(u => u.Etat).HasColumnName("etat");
            });
            //Zone
            modelBuilder.Entity<Zone>(entity =>
            {
                entity.ToTable("zone");
                entity.HasKey(z => z.Id);
                entity.Property(z => z.Id).HasColumnName("id");
                entity.Property(z => z.Nom).HasColumnName("nom");
                entity.Property(z => z.PrixLivraison).HasColumnName("prix_livraison");
                entity.Property(z => z.Etat).HasColumnName("etat");
            });
            //Livreur
            modelBuilder.Entity<Livreur>(entity =>
            {
                entity.ToTable("livreur");
                entity.HasKey(l => l.Id);
                entity.Property(l => l.Id).HasColumnName("id");
                entity.Property(l => l.NomComplet).HasColumnName("nom_complet");
                entity.Property(l => l.Telephone).HasColumnName("telephone");
                entity.Property(l => l.ZoneId).HasColumnName("zone_id");
                entity.Property(l => l.Etat).HasColumnName("etat");
            });
            //Burger
            modelBuilder.Entity<Burger>(entity =>
            {
                entity.ToTable("burger");
                entity.HasKey(b => b.Id);
                entity.Property(b => b.Id).HasColumnName("id");
                entity.Property(b => b.Nom).HasColumnName("nom");
                entity.Property(b => b.Prix).HasColumnName("prix");
                entity.Property(b => b.Image).HasColumnName("image");
                entity.Property(b => b.Etat).HasColumnName("etat");
            });
            //Menu
            modelBuilder.Entity<Menu>(entity =>
            {
                entity.ToTable("menu");
                entity.HasKey(m => m.Id);
                entity.Property(m => m.Id).HasColumnName("id");
                entity.Property(m => m.Nom).HasColumnName("nom");
                entity.Property(m => m.Image).HasColumnName("image");
                entity.Property(m => m.Etat).HasColumnName("etat");
                entity.Property(m => m.Montant).HasColumnName("montant");
                entity.Property(m => m.BurgerId).HasColumnName("burger_id");
            });
            //Complement
            modelBuilder.Entity<Complement>(entity =>
            {
                entity.ToTable("complement");
                entity.HasKey(c => c.Id);
                entity.Property(c => c.Id).HasColumnName("id");
                entity.Property(c => c.Nom).HasColumnName("nom");
                entity.Property(c => c.Prix).HasColumnName("prix");
                entity.Property(c => c.Image).HasColumnName("image");
                entity.Property(c => c.Etat).HasColumnName("etat");
                entity.Property(c => c.Categorie).HasColumnName("categorie").HasConversion<string>();
            });
            //MenuComplement
            modelBuilder.Entity<MenuComplement>(entity =>
            {
                entity.ToTable("menu_complement");
                entity.HasKey(mc => new { mc.MenuId, mc.ComplementId });
                entity.Property(mc => mc.MenuId).HasColumnName("menu_id");
                entity.Property(mc => mc.ComplementId).HasColumnName("complement_id");
            });
            //Commande
            modelBuilder.Entity<Commande>(entity =>
            {
                entity.ToTable("commande");
                entity.HasKey(c => c.Id);
                entity.Property(c => c.Id).HasColumnName("id");
                entity.Property(c => c.DateCommande).HasColumnName("datecommande");
                entity.Property(c => c.Etat).HasColumnName("etat").HasConversion(
                    v => v.ToString(),
                    v => (EtatCommande)Enum.Parse(typeof(EtatCommande), v
                ));
                entity.Property(c => c.Type).HasColumnName("type").HasConversion<string>();
                entity.Property(c => c.MontantTotal).HasColumnName("montant_total");
                entity.Property(c => c.ClientId).HasColumnName("client_id");
                entity.Property(c => c.ZoneId).HasColumnName("zone_id");
                entity.Property(c => c.LivreurId).HasColumnName("livreur_id");
            });
            //CommandeItem
            modelBuilder.Entity<CommandeItem>(entity =>
            {
                entity.ToTable("commande_item");
                entity.HasKey(ci => ci.Id);
                entity.Property(ci => ci.Id).HasColumnName("id");
                entity.Property(ci => ci.CommandeId).HasColumnName("commande_id");
                entity.HasOne(ci => ci.Commande)
                    .WithMany(c => c.CommandeItems)
                    .HasForeignKey(ci => ci.CommandeId)
                    .OnDelete(DeleteBehavior.Cascade);
                entity.Property(ci => ci.Type).HasColumnName("type").HasConversion<string>();
                entity.Property(ci => ci.ProduitId).HasColumnName("produit_id");
                entity.Property(ci => ci.Quantite).HasColumnName("quantite");
                entity.Property(ci => ci.PrixUnitaire).HasColumnName("prix_unitaire");
            });
            //Paiement
            modelBuilder.Entity<Paiement>(entity =>
            {
                entity.ToTable("paiement");
                entity.HasKey(p => p.Id);
                entity.Property(p => p.Id).HasColumnName("id");
                entity.Property(p => p.DatePaiement).HasColumnName("date_paiement");
                entity.Property(p => p.Montant).HasColumnName("montant");
                entity.Property(p => p.Mode).HasColumnName("mode").HasConversion<string>();
                entity.Property(p => p.CommandeId).HasColumnName("commande_id");
            });
        }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseNpgsql(
                    "Host=ep-plain-tree-aht27lqk-pooler.c-3.us-east-1.aws.neon.tech;" +
                    "Database=brasilBurger;" +
                    "Username=neondb_owner;" +
                    "Password=npg_pAj8FHkKlZL6;" +
                    "Ssl Mode=Require;" +
                    "Trust Server Certificate=true"
                );
            }
        }
    }
}
