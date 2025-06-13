using System.Net.Http.Headers;
using System.Text.Json;
using PddTrainer.Domain.Abstractions;
using PddTrainer.Domain.DTOs;

namespace PddTrainer.Domain.Services;

public class SignRecognitionService(HttpClient httpClient) : ISignRecognitionService
{
    public async Task<SignRecognitionResult> RecognizeSignAsync(IFormFile file)
    {
        using var memoryStream = new MemoryStream();
        await file.CopyToAsync(memoryStream);
        memoryStream.Position = 0;

        var content = new MultipartFormDataContent();
        var fileContent = new StreamContent(memoryStream);
        fileContent.Headers.ContentType = MediaTypeHeaderValue.Parse(file.ContentType);
        content.Add(fileContent, "file", file.FileName);

        var response = await httpClient.PostAsync("http://127.0.0.1:5000/predict", content);
        response.EnsureSuccessStatusCode();

        var json = await response.Content.ReadAsStringAsync();
        var result = JsonSerializer.Deserialize<SignRecognitionResult>(json, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });

        return result!;
    }
}
