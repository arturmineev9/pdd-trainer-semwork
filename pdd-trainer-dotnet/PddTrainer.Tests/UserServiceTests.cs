using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Moq;
using PddTrainer.Domain.DTOs;
using PddTrainer.Models;
using PddTrainer.Domain.Services;
using PddTrainer.Infrastructure.Data;
using Xunit;

namespace PddTrainer.Tests;

public class UserServiceTests : IDisposable
{
    private readonly AppDbContext _context;
    private readonly Mock<IJwtTokenService> _jwtTokenServiceMock;
    private readonly Mock<IPasswordHasher<User>> _passwordHasherMock;
    private readonly UserService _userService;

    public UserServiceTests()
    {
        var options = new DbContextOptionsBuilder<AppDbContext>()
            .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
            .Options;
        _context = new AppDbContext(options);

        _jwtTokenServiceMock = new Mock<IJwtTokenService>();
        _passwordHasherMock = new Mock<IPasswordHasher<User>>();

        _userService = new UserService(_context, _jwtTokenServiceMock.Object, _passwordHasherMock.Object);
    }

    [Fact]
    public async Task RegisterAsync_ShouldCreateUserAndReturnToken_WhenEmailIsUnique()
    {
        // Arrange
        var request = new RegisterRequest { FirstName = "Test", Email = "test@example.com", Password = "password123" };
        var expectedToken = "test_token";

        _passwordHasherMock.Setup(p => p.HashPassword(It.IsAny<User>(), request.Password)).Returns("hashed_password");
        _jwtTokenServiceMock.Setup(j => j.GenerateToken(It.IsAny<User>())).Returns(expectedToken);

        // Act
        var token = await _userService.RegisterAsync(request);

        // Assert
        Assert.Equal(expectedToken, token);
        Assert.Single(_context.Users);
        var userInDb = _context.Users.First();
        Assert.Equal(request.Email, userInDb.Email);
        Assert.Equal("hashed_password", userInDb.PasswordHash);
    }

    [Fact]
    public async Task RegisterAsync_ShouldThrowException_WhenEmailExists()
    {
        // Arrange
        var existingUser = new User { Email = "test@example.com", PasswordHash = "some_hash" };
        _context.Users.Add(existingUser);
        await _context.SaveChangesAsync();

        var request = new RegisterRequest { Email = "test@example.com", Password = "password123" };

        // Act & Assert
        var exception = await Assert.ThrowsAsync<Exception>(() => _userService.RegisterAsync(request));
        Assert.Equal("Пользователь с таким email уже существует", exception.Message);
    }
    
    [Fact]
    public async Task LoginAsync_ShouldReturnToken_WhenCredentialsAreValid()
    {
        // Arrange
        var user = new User { Email = "test@example.com", PasswordHash = "hashed_password" };
        _context.Users.Add(user);
        await _context.SaveChangesAsync();

        var request = new LoginRequest { Email = "test@example.com", Password = "password123" };
        var expectedToken = "valid_token";

        _passwordHasherMock.Setup(p => p.VerifyHashedPassword(user, user.PasswordHash, request.Password))
            .Returns(PasswordVerificationResult.Success);
        _jwtTokenServiceMock.Setup(j => j.GenerateToken(user)).Returns(expectedToken);

        // Act
        var token = await _userService.LoginAsync(request);

        // Assert
        Assert.Equal(expectedToken, token);
    }

    [Fact]
    public async Task LoginAsync_ShouldThrowException_WhenUserNotFound()
    {
        // Arrange
        var request = new LoginRequest { Email = "nonexistent@example.com", Password = "password123" };

        // Act & Assert
        var exception = await Assert.ThrowsAsync<Exception>(() => _userService.LoginAsync(request));
        Assert.Equal("Пользователь не найден", exception.Message);
    }

    [Fact]
    public async Task LoginAsync_ShouldThrowException_WhenPasswordIsInvalid()
    {
        // Arrange
        var user = new User { Email = "test@example.com", PasswordHash = "hashed_password" };
        _context.Users.Add(user);
        await _context.SaveChangesAsync();

        var request = new LoginRequest { Email = "test@example.com", Password = "wrong_password" };

        _passwordHasherMock.Setup(p => p.VerifyHashedPassword(user, user.PasswordHash, request.Password))
            .Returns(PasswordVerificationResult.Failed);
        
        // Act & Assert
        var exception = await Assert.ThrowsAsync<Exception>(() => _userService.LoginAsync(request));
        Assert.Equal("Неверный пароль", exception.Message);
    }


    public void Dispose()
    {
        _context.Database.EnsureDeleted();
        _context.Dispose();
    }
}