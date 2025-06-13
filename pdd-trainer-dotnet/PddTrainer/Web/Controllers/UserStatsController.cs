using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using PddTrainer.Domain.Abstractions;
using PddTrainer.Domain.DTOs;

namespace PddTrainer.Web.Controllers;

[ApiController]
[Route("api/user-stats")]
[Authorize]
public class UserStatsController(IUserService userService) : ControllerBase
{
    [HttpGet]
    public async Task<IActionResult> GetStats()
    {
        try
        {
            var stats = await userService.GetUserStatsAsync(User);
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
            await userService.SaveUserStatsAsync(User, request);
            return Ok();
        }
        catch (Exception ex)
        {
            return BadRequest(new { error = ex.Message });
        }
    }
} 