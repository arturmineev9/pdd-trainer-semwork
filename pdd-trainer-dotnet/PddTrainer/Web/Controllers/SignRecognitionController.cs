using System.Text.Json;
using PddTrainer.Domain.Abstractions;
using PddTrainer.Domain.DTOs;

namespace PddTrainer.Web.Controllers;

using Microsoft.AspNetCore.Mvc;

using Microsoft.Extensions.Logging;
// ...

[ApiController]
[Route("api/v1/sign-recognition")]
public class SignRecognitionController(
    ISignRecognitionService recognitionService,
    ILogger<SignRecognitionController> logger) : ControllerBase
{
    [HttpPost]
    public async Task<IActionResult> Recognize([FromForm] SignRecognitionRequest request)
    {
        var file = request.File;
        if (file == null || file.Length == 0)
        {
            logger.LogWarning("Файл не загружен или пустой.");
            return BadRequest("Файл не загружен.");
        }

        logger.LogInformation("Получен файл: {FileName}, размер: {Size} байт", file.FileName, file.Length);

        try
        {
            var result = await recognitionService.RecognizeSignAsync(file);
            logger.LogInformation("Результат распознавания: {Result}", JsonSerializer.Serialize(result));
            return Ok(result);
        }
        catch (Exception ex)
        {
            logger.LogError(ex, "Ошибка при распознавании знака");
            return StatusCode(500, new { error = ex.Message });
        }
    }
}
