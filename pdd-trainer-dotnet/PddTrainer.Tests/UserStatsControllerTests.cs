using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Moq;
using PddTrainer.Domain.DTOs;
using PddTrainer.Web.Controllers;
using System.Security.Claims;
using PddTrainer.Domain.Abstractions;
using Xunit;

namespace PddTrainer.Tests;

public class UserStatsControllerTests
{
    private readonly Mock<IUserService> _userServiceMock;
    private readonly UserStatsController _controller;

    public UserStatsControllerTests()
    {
        _userServiceMock = new Mock<IUserService>();
        _controller = new UserStatsController(_userServiceMock.Object);

        // Mock HttpContext и User
        var user = new ClaimsPrincipal(new ClaimsIdentity(new Claim[]
        {
            new(ClaimTypes.NameIdentifier, "1"),
        }, "mock"));

        _controller.ControllerContext = new ControllerContext()
        {
            HttpContext = new DefaultHttpContext() { User = user }
        };
    }

    [Fact]
    public async Task GetStats_ShouldReturnOkWithStats()
    {
        // Arrange
        var statsDto = new List<UserStatsDto>();
        _userServiceMock.Setup(s => s.GetUserStatsAsync(It.IsAny<ClaimsPrincipal>()))
            .ReturnsAsync(statsDto);

        // Act
        var result = await _controller.GetStats();

        // Assert
        var okResult = Assert.IsType<OkObjectResult>(result);
        Assert.Same(statsDto, okResult.Value);
    }

    [Fact]
    public async Task SaveStats_ShouldReturnOk()
    {
        // Arrange
        var request = new UpdateUserStatsRequest();
        _userServiceMock.Setup(s => s.SaveUserStatsAsync(It.IsAny<ClaimsPrincipal>(), request))
            .Returns(Task.CompletedTask);

        // Act
        var result = await _controller.SaveStats(request);

        // Assert
        Assert.IsType<OkResult>(result);
    }
} 