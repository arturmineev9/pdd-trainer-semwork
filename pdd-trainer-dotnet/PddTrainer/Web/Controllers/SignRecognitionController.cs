using PddTrainer.Domain.DTOs;

namespace PddTrainer.Web.Controllers;

using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("api/v1/sign-recognition")]
public class SignRecognitionController(IHttpClientFactory httpClientFactory) : ControllerBase
{
    [HttpPost]
    public async Task<IActionResult> Recognize([FromForm] SignRecognitionRequest request)
    {
        var file = request.File;
        if (file == null || file.Length == 0)
            return BadRequest("Файл не загружен.");

        using var memoryStream = new MemoryStream();
        await file.CopyToAsync(memoryStream);
        memoryStream.Position = 0;

        var httpClient = httpClientFactory.CreateClient();

        var flaskRequest = new MultipartFormDataContent();
        flaskRequest.Add(new StreamContent(memoryStream), "file", file.FileName);

        var flaskResponse = await httpClient.PostAsync("http://127.0.0.1:5000/predict", flaskRequest);

        if (!flaskResponse.IsSuccessStatusCode)
            return StatusCode((int)flaskResponse.StatusCode);

        var resultJson = await flaskResponse.Content.ReadAsStringAsync();
        return Content(resultJson, "application/json");
    }
}
