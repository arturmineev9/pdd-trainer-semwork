using PddTrainer.Models;

namespace PddTrainer.Domain.Services
{
    public interface IJwtTokenService
    {
        string GenerateToken(User user);
    }
}
