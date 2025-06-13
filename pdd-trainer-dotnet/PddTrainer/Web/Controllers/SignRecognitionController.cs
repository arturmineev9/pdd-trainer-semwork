using PddTrainer.Domain.Abstractions;
using PddTrainer.Domain.DTOs;

namespace PddTrainer.Web.Controllers;

using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("api/v1/sign-recognition")]
public class SignRecognitionController(ISignRecognitionService recognitionService) : ControllerBase
{
    [HttpPost]
    public async Task<IActionResult> Recognize([FromForm] SignRecognitionRequest request)
    {
        var file = request.File;
        if (file == null || file.Length == 0)
            return BadRequest("Файл не загружен.");

        try
        {
            var result = await recognitionService.RecognizeSignAsync(file);
            return Ok(result);
        }
        catch (Exception ex)
        {
            return StatusCode(500, new { error = ex.Message });
        }
    }
}
