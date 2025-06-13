using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using PddTrainer.Domain.Abstractions;
using PddTrainer.DTOs;

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
        try
        {
            var stats = await _userService.GetUserStatsAsync(User);
            return Ok(stats);
        }
        catch (Exception ex)
        {
            return BadRequest(new { error = ex.Message });
        }
    }

    [HttpPost]
    public async Task<IActionResult> SaveStats([FromBody] UpdateUserStatsRequest request)
    {
        try
        {
            await _userService.SaveUserStatsAsync(User, request);
            return Ok();
        }
        catch (Exception ex)
        {
            return BadRequest(new { error = ex.Message });
        }
    }
} 