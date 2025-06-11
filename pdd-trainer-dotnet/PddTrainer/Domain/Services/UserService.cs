using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using PddTrainer.DTOs;
using PddTrainer.Models;
using PddTrainer.Services;

namespace PddTrainer.Infrastructure.Services;

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
}