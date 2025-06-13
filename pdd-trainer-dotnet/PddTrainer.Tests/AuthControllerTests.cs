using Microsoft.AspNetCore.Mvc;
using Moq;
using PddTrainer.Domain.DTOs;
using PddTrainer.Web.Controllers;
using PddTrainer.Domain.Abstractions;
using Xunit;

namespace PddTrainer.Tests;

public class AuthControllerTests
{
    private readonly Mock<IUserService> _userServiceMock;
    private readonly AuthController _authController;

    public AuthControllerTests()
    {
        _userServiceMock = new Mock<IUserService>();
        _authController = new AuthController(_userServiceMock.Object);
    }

    [Fact]
    public async Task Register_ShouldReturnOk_WhenRegistrationIsSuccessful()
    {
        // Arrange
        var request = new RegisterRequest();
        var expectedToken = "new_user_token";
        _userServiceMock.Setup(s => s.RegisterAsync(request)).ReturnsAsync(expectedToken);

        // Act
        var result = await _authController.Register(request);

        // Assert
        var okResult = Assert.IsType<OkObjectResult>(result);
        var returnValue = okResult.Value;
        Assert.NotNull(returnValue);
        var tokenProperty = returnValue.GetType().GetProperty("token");
        Assert.NotNull(tokenProperty);
        Assert.Equal(expectedToken, tokenProperty.GetValue(returnValue, null));
    }

    [Fact]
    public async Task Register_ShouldReturnBadRequest_WhenServiceThrowsException()
    {
        // Arrange
        var request = new RegisterRequest();
        var errorMessage = "Registration failed";
        _userServiceMock.Setup(s => s.RegisterAsync(request)).ThrowsAsync(new Exception(errorMessage));

        // Act
        var result = await _authController.Register(request);

        // Assert
        var badRequestResult = Assert.IsType<BadRequestObjectResult>(result);
        var errorValue = badRequestResult.Value;
        Assert.NotNull(errorValue);
        var errorProperty = errorValue.GetType().GetProperty("error");
        Assert.NotNull(errorProperty);
        Assert.Equal(errorMessage, errorProperty.GetValue(errorValue, null));
    }

    [Fact]
    public async Task Login_ShouldReturnOk_WhenLoginIsSuccessful()
    {
        // Arrange
        var request = new LoginRequest();
        var expectedToken = "existing_user_token";
        _userServiceMock.Setup(s => s.LoginAsync(request)).ReturnsAsync(expectedToken);

        // Act
        var result = await _authController.Login(request);

        // Assert
        var okResult = Assert.IsType<OkObjectResult>(result);
        var returnValue = okResult.Value;
        Assert.NotNull(returnValue);
        var tokenProperty = returnValue.GetType().GetProperty("token");
        Assert.NotNull(tokenProperty);
        Assert.Equal(expectedToken, tokenProperty.GetValue(returnValue, null));
    }

    [Fact]
    public async Task Login_ShouldReturnBadRequest_WhenServiceThrowsException()
    {
        // Arrange
        var request = new LoginRequest();
        var errorMessage = "Login failed";
        _userServiceMock.Setup(s => s.LoginAsync(request)).ThrowsAsync(new Exception(errorMessage));

        // Act
        var result = await _authController.Login(request);

        // Assert
        var badRequestResult = Assert.IsType<BadRequestObjectResult>(result);
        var errorValue = badRequestResult.Value;
        Assert.NotNull(errorValue);
        var errorProperty = errorValue.GetType().GetProperty("error");
        Assert.NotNull(errorProperty);
        Assert.Equal(errorMessage, errorProperty.GetValue(errorValue, null));
    }
} 