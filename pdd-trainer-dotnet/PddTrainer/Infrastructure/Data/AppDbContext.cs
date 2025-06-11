using Microsoft.EntityFrameworkCore;
using PddTrainer.Domain.Models;
using PddTrainer.Models;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

    public DbSet<User> Users => Set<User>();

    public DbSet<UserStats> UserStats => Set<UserStats>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<User>()
            .HasIndex(u => u.Email)
            .IsUnique();

        modelBuilder.Entity<UserStats>()
            .HasOne(s => s.User)
            .WithMany(u => u.Stats)
            .HasForeignKey(s => s.UserId);
    }
}