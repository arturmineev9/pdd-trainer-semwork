namespace PddTrainer.Services;
using PddTrainer.DTOs;

public interface IUserService
{
    Task<string> RegisterAsync(RegisterRequest request);
    Task<string> LoginAsync(LoginRequest request);
}