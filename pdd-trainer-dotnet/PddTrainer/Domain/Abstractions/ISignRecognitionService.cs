using PddTrainer.Domain.DTOs;

namespace PddTrainer.Application.Interfaces;

public interface ISignRecognitionService
{
    Task<SignRecognitionResult> RecognizeSignAsync(IFormFile file);
}