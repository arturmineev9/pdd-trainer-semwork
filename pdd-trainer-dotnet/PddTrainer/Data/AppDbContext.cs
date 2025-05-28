using Microsoft.EntityFrameworkCore;
using PddTrainer.Models;

namespace PddTrainer.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

    public DbSet<User> Users => Set<User>();
    public DbSet<UserStats> UserStats => Set<UserStats>();
}