using System.Text.Json.Serialization;

namespace PddTrainer.Domain.DTOs;

public class SignRecognitionResult
{
    [JsonPropertyName("predicted_class")]
    public int ClassIndex { get; set; }
}
