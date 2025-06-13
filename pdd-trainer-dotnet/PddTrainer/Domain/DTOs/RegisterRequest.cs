using System.ComponentModel.DataAnnotations;

namespace PddTrainer.Domain.DTOs;

public class RegisterRequest
{
    [Required(ErrorMessage = "Имя пользователя обязательно")]
    public string FirstName { get; set; } = string.Empty;
    
    [Required(ErrorMessage = "Email обязателен")]
    [EmailAddress(ErrorMessage = "Неверный формат Email")]
    public string Email { get; set; } = string.Empty;

    [Required(ErrorMessage = "Пароль обязателен")]
    [MinLength(6, ErrorMessage = "Пароль должен быть не менее 6 символов")]
    public string Password { get; set; } = string.Empty;
}