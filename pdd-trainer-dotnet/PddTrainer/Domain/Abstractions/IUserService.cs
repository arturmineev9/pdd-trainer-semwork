namespace PddTrainer.Services;
using PddTrainer.DTOs;
using System.Security.Claims;

public interface IUserService
{
    Task<string> RegisterAsync(RegisterRequest request);
    Task<string> LoginAsync(LoginRequest request);
    Task<List<UserStatsDto>> GetUserStatsAsync(ClaimsPrincipal user);
    Task SaveUserStatsAsync(ClaimsPrincipal user, UpdateUserStatsRequest request);
}