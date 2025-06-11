using System.Security.Claims;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using PddTrainer.Domain.Abstractions;
using PddTrainer.Domain.DTOs;
using PddTrainer.Domain.Models;
using PddTrainer.DTOs;
using PddTrainer.Infrastructure.Services;
using PddTrainer.Models;

namespace PddTrainer.Domain.Services;

public class UserService : IUserService
{
    private readonly AppDbContext _dbContext;
    private readonly JwtTokenService _jwtTokenService;
    private readonly IPasswordHasher<User> _passwordHasher;

    public UserService(AppDbContext dbContext, JwtTokenService jwtTokenService, IPasswordHasher<User> passwordHasher)
    {
        _dbContext = dbContext;
        _jwtTokenService = jwtTokenService;
        _passwordHasher = passwordHasher;
    }

    public async Task<string> RegisterAsync(RegisterRequest request)
    {
        if (await _dbContext.Users.AnyAsync(u => u.Email == request.Email))
            throw new Exception("Пользователь с таким email уже существует");

        var user = new User
        {
            FirstName = request.FirstName,
            Email = request.Email
        };
        user.PasswordHash = _passwordHasher.HashPassword(user, request.Password);

        _dbContext.Users.Add(user);
        await _dbContext.SaveChangesAsync();

        return _jwtTokenService.GenerateToken(user);
    }

    public async Task<string> LoginAsync(LoginRequest request)
    {
        var user = await _dbContext.Users.FirstOrDefaultAsync(u => u.Email == request.Email);
        if (user == null)
            throw new Exception("Пользователь не найден");

        var result = _passwordHasher.VerifyHashedPassword(user, user.PasswordHash, request.Password);
        if (result == PasswordVerificationResult.Failed)
            throw new Exception("Неверный пароль");

        return _jwtTokenService.GenerateToken(user);
    }

    public async Task<List<UserStatsDto>> GetUserStatsAsync(ClaimsPrincipal userPrincipal)
    {
        var userId = int.Parse(userPrincipal.FindFirst(ClaimTypes.NameIdentifier)?.Value ??
                               userPrincipal.FindFirst(System.IdentityModel.Tokens.Jwt.JwtRegisteredClaimNames.Sub)!.Value);
        var stats = await _dbContext.UserStats
            .Where(s => s.UserId == userId)
            .Select(s => new UserStatsDto
            {
                VariantNumber = s.VariantNumber,
                CorrectAnswers = s.CorrectAnswers,
                UpdatedAt = s.UpdatedAt
            })
            .ToListAsync();
        return stats;
    }

    public async Task SaveUserStatsAsync(ClaimsPrincipal userPrincipal, UpdateUserStatsRequest request)
    {
        var userId = int.Parse(userPrincipal.FindFirst(ClaimTypes.NameIdentifier)?.Value ??
                               userPrincipal.FindFirst(System.IdentityModel.Tokens.Jwt.JwtRegisteredClaimNames.Sub)!.Value);
        var existingStats = await _dbContext.UserStats.Where(s => s.UserId == userId).ToListAsync();
        foreach (var stat in request.Stats)
        {
            var stats = existingStats.FirstOrDefault(s => s.VariantNumber == stat.VariantNumber);
            if (stats == null)
            {
                stats = new UserStats
                {
                    UserId = userId,
                    VariantNumber = stat.VariantNumber,
                    CorrectAnswers = stat.CorrectAnswers,
                    UpdatedAt = DateTime.UtcNow
                };
                _dbContext.UserStats.Add(stats);
            }
            else
            {
                stats.CorrectAnswers = stat.CorrectAnswers;
                stats.UpdatedAt = DateTime.UtcNow;
            }
        }
        await _dbContext.SaveChangesAsync();
    }
}
