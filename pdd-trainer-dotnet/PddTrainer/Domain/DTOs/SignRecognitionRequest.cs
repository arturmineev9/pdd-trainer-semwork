using System.ComponentModel.DataAnnotations;

namespace PddTrainer.Domain.DTOs
{
    public class SignRecognitionRequest
    {
        [Required]
        public IFormFile File { get; set; }
    }
}