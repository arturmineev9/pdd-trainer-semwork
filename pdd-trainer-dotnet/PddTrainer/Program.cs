using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Text;
using PddTrainer.Models;

var builder = WebApplication.CreateBuilder(args);

// DB
builder.Services.AddDbContext<AppDbContext>(optionsBuilder =>
{
    optionsBuilder.UseSnakeCaseNamingConvention();
    optionsBuilder.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection"));
});

// Auth
var key = Encoding.ASCII.GetBytes("SuperSecretKeyThatYouShouldChange");
builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options => {
        options.TokenValidationParameters = new TokenValidationParameters {
            ValidateIssuerSigningKey = true,
            IssuerSigningKey = new SymmetricSecurityKey(key),
            ValidateIssuer = false,
            ValidateAudience = false
        };
    });

builder.Services.AddAuthorization();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddHttpClient();

var app = builder.Build();

app.UseSwagger();
app.UseSwaggerUI();

app.UseAuthentication();
app.UseAuthorization();

app.MapGet("/", () => "PDD Server is running");

app.MapPost("/api/recognize-sign", async (HttpRequest request, IHttpClientFactory httpClientFactory) =>
{
    var file = request.Form.Files.GetFile("file");

    if (file == null || file.Length == 0)
        return Results.BadRequest("No file uploaded");

    using var memoryStream = new MemoryStream();
    await file.CopyToAsync(memoryStream);
    memoryStream.Seek(0, SeekOrigin.Begin);

    var client = httpClientFactory.CreateClient();

    using var form = new MultipartFormDataContent();
    var fileContent = new StreamContent(memoryStream);
    fileContent.Headers.ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("image/jpeg");
    form.Add(fileContent, "file", file.FileName);

    var response = await client.PostAsync("http://localhost:5000/predict", form);
    if (!response.IsSuccessStatusCode)
        return Results.StatusCode((int)response.StatusCode);

    var responseContent = await response.Content.ReadFromJsonAsync<TrafficSignPredictionResult>();
    return Results.Ok(responseContent);
})
.Accepts<IFormFile>("multipart/form-data")
.Produces<TrafficSignPredictionResult>(StatusCodes.Status200OK)
.WithName("RecognizeSign")
.WithOpenApi();


app.Run();