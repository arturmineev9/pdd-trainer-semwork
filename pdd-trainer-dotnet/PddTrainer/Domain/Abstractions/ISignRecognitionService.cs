using PddTrainer.Domain.DTOs;

namespace PddTrainer.Domain.Abstractions;

public interface ISignRecognitionService
{
    Task<SignRecognitionResult> RecognizeSignAsync(IFormFile file);
}