using brasilBurger.Data;
using brasilBurger.Services;
using brasilBurger.Models;
using brasilBurger.Services.Impl;
using Microsoft.EntityFrameworkCore;
using Npgsql;


var builder = WebApplication.CreateBuilder(args);
var connectionString = "Host=ep-plain-tree-aht27lqk-pooler.c-3.us-east-1.aws.neon.tech;" +
                    "Database=brasilBurger;" +
                    "Username=neondb_owner;" +
                    "Password=npg_pAj8FHkKlZL6;" +
                    "Ssl Mode=Require;" +
                    "Trust Server Certificate=true";
var dataSourceBuilder = new NpgsqlDataSourceBuilder(connectionString);
dataSourceBuilder.MapEnum<EtatCommande>("etat_commande");
dataSourceBuilder.MapEnum<TypeCommande>("type_commande");
dataSourceBuilder.MapEnum<TypeCommandeItem>("type_commande_item");
dataSourceBuilder.MapEnum<ModePaiement>("mode_paiement");
dataSourceBuilder.MapEnum<RoleUser>("role_user");
dataSourceBuilder.MapEnum<CategorieComplement>("categorie_complement");
var dataSource = dataSourceBuilder.Build();
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(dataSource)
);

builder.Services.AddDbContext<AppDbContext>();
builder.Services.AddScoped<ICatalogueServices, CatalogueServices>();
builder.Services.AddScoped<ICommandeServices, CommandeServices>();
builder.Services.AddScoped<IPaiementServices, PaiementServices>();
builder.Services.AddScoped<IUserServices, UserServices>();


// Add services to the container.
builder.Services.AddControllersWithViews();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Catalogue}/{action=Index}/{id?}");

app.Run();
