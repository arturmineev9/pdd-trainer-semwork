namespace PddTrainer.Models;

public class UserStats
{
    public int Id { get; set; }

    public int UserId { get; set; }

    public User User { get; set; } = null!;

    public int VariantNumber { get; set; }

    public int CorrectAnswers { get; set; }

    public DateTime UpdatedAt { get; set; } = DateTime.UtcNow;
}
