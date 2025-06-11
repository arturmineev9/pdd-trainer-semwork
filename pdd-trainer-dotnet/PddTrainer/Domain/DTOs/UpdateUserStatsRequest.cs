namespace PddTrainer.DTOs;

public class UpdateUserStatsRequest
{
    public List<UserStatsDto> Stats { get; set; } = new();
} 