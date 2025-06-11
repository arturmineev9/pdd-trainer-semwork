using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using PddTrainer.DTOs;
using PddTrainer.Services;

namespace PddTrainer.Web.Controllers;

[ApiController]
[Route("api/[controller]")]
[Authorize]
public class UserStatsController : ControllerBase
{
    private readonly IUserService _userService;

    public UserStatsController(IUserService userService)
    {
        _userService = userService;
    }

    [HttpGet]
    public async Task<IActionResult> GetStats()
    {
        var stats = await _userService.GetUserStatsAsync(User);
        return Ok(stats);
    }

    [HttpPost]
    public async Task<IActionResult> SaveStats([FromBody] UpdateUserStatsRequest request)
    {
        await _userService.SaveUserStatsAsync(User, request);
        return Ok();
    }
} 