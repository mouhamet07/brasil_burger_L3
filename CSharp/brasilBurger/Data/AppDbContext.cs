using brasilBurger.Models;
using Microsoft.EntityFrameworkCore;

public class BrasilBurgerDbContext : DbContext
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
        // TABLE NAMES
        modelBuilder.Entity<User>().ToTable("user");
        modelBuilder.Entity<Zone>().ToTable("zone");
        modelBuilder.Entity<Livreur>().ToTable("livreur");
        modelBuilder.Entity<Burger>().ToTable("burger");
        modelBuilder.Entity<Menu>().ToTable("menu");
        modelBuilder.Entity<Complement>().ToTable("complement");
        modelBuilder.Entity<MenuComplement>().ToTable("menu_complement");
        modelBuilder.Entity<Commande>().ToTable("commande");
        modelBuilder.Entity<CommandeItem>().ToTable("commande_item");
        modelBuilder.Entity<Paiement>().ToTable("paiement");

        // ENUMS AS STRING
        modelBuilder.Entity<User>().Property(u => u.Role).HasConversion<string>();
        modelBuilder.Entity<Commande>().Property(c => c.Etat).HasConversion<string>();
        modelBuilder.Entity<Commande>().Property(c => c.Type).HasConversion<string>();
        modelBuilder.Entity<Paiement>().Property(p => p.Mode).HasConversion<string>();
        modelBuilder.Entity<Complement>().Property(c => c.Categorie).HasConversion<string>();

        // MENU_COMPLEMENT (ManyToMany)
        modelBuilder.Entity<MenuComplement>()
            .HasKey(mc => new { mc.MenuId, mc.ComplementId });

        modelBuilder.Entity<MenuComplement>()
            .HasOne(mc => mc.Menu)
            .WithMany(m => m.MenuComplements)
            .HasForeignKey(mc => mc.MenuId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<MenuComplement>()
            .HasOne(mc => mc.Complement)
            .WithMany(c => c.MenuComplements)
            .HasForeignKey(mc => mc.ComplementId)
            .OnDelete(DeleteBehavior.Cascade);

        // ONE TO ONE : COMMANDE -> PAIEMENT
        modelBuilder.Entity<Paiement>()
            .HasOne(p => p.Commande)
            .WithOne(c => c.PaiementC)
            .HasForeignKey<Paiement>(p => p.CommandeId)
            .OnDelete(DeleteBehavior.Cascade);

        // ONE TO MANY
        modelBuilder.Entity<Livreur>()
            .HasOne(l => l.Zone)
            .WithMany(z => z.Livreurs)
            .HasForeignKey(l => l.ZoneId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Menu>()
            .HasOne(m => m.Burger)
            .WithMany(b => b.Menus)
            .HasForeignKey(m => m.BurgerId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Commande>()
            .HasOne(c => c.Client)
            .WithMany(u => u.Commandes)
            .HasForeignKey(c => c.ClientId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Commande>()
            .HasOne(c => c.Zone)
            .WithMany(z => z.Commandes)
            .HasForeignKey(c => c.ZoneId)
            .OnDelete(DeleteBehavior.SetNull);

        modelBuilder.Entity<Commande>()
            .HasOne(c => c.Livreur)
            .WithMany(l => l.Commandes)
            .HasForeignKey(c => c.LivreurId)
            .OnDelete(DeleteBehavior.SetNull);

        modelBuilder.Entity<CommandeItem>()
            .HasOne(ci => ci.Commande)
            .WithMany(c => c.CommandeItems)
            .HasForeignKey(ci => ci.CommandeId)
            .OnDelete(DeleteBehavior.Cascade);

        // INDEXES
        modelBuilder.Entity<Commande>().HasIndex(c => c.ClientId);
        modelBuilder.Entity<Commande>().HasIndex(c => c.Etat);
        modelBuilder.Entity<Commande>().HasIndex(c => c.DateCommande);
        modelBuilder.Entity<Commande>().HasIndex(c => c.Type);
        modelBuilder.Entity<Commande>().HasIndex(c => c.ZoneId);

        modelBuilder.Entity<CommandeItem>().HasIndex(ci => ci.CommandeId);
        modelBuilder.Entity<CommandeItem>().HasIndex(ci => ci.Type);
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
