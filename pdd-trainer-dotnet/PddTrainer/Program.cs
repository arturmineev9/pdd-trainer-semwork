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

app.MapPost("/api/v1/sign-recognition", async (HttpRequest request, IHttpClientFactory httpClientFactory) =>
{
    var form = await request.ReadFormAsync();
    var file = form.Files.GetFile("file");

    if (file == null || file.Length == 0)
        return Results.BadRequest("Файл не загружен.");

    using var memoryStream = new MemoryStream();
    await file.CopyToAsync(memoryStream);
    memoryStream.Position = 0;

    var httpClient = httpClientFactory.CreateClient();

    var flaskRequest = new MultipartFormDataContent();
    flaskRequest.Add(new StreamContent(memoryStream), "file", file.FileName);

    var flaskResponse = await httpClient.PostAsync("http://127.0.0.1:5000/predict", flaskRequest);
    if (!flaskResponse.IsSuccessStatusCode)
        return Results.StatusCode((int)flaskResponse.StatusCode);

    var resultJson = await flaskResponse.Content.ReadAsStringAsync();
    return Results.Content(resultJson, "application/json");
});


app.Run();