using System.Security.Claims;
using PddTrainer.Domain.DTOs;

namespace PddTrainer.Domain.Abstractions;

public interface IUserService
{
    Task<string> RegisterAsync(RegisterRequest request);
    Task<string> LoginAsync(LoginRequest request);
    Task<List<UserStatsDto>> GetUserStatsAsync(ClaimsPrincipal user);
    Task SaveUserStatsAsync(ClaimsPrincipal user, UpdateUserStatsRequest request);
}