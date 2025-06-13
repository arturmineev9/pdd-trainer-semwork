using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using System.Text;
using PddTrainer.Domain.Services;
using Microsoft.AspNetCore.Identity;
using PddTrainer.Domain.Abstractions;
using PddTrainer.Infrastructure.Data;
using PddTrainer.Models;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>(options =>
{
    options.UseSnakeCaseNamingConvention();
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection"));
});

var jwtSection = builder.Configuration.GetSection("JwtSettings");
var key = Encoding.ASCII.GetBytes(jwtSection["Key"]!);
builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options =>
    {
        options.TokenValidationParameters = new TokenValidationParameters
        {
            ValidateIssuerSigningKey = true,
            IssuerSigningKey = new SymmetricSecurityKey(key),
            ValidateIssuer = true,
            ValidateAudience = true,
            ValidIssuer = jwtSection["Issuer"],
            ValidAudience = jwtSection["Audience"]
        };
    });

builder.Services.AddAuthorization();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddHttpClient();
builder.Services.AddHttpClient<ISignRecognitionService, SignRecognitionService>(client =>
{
    var baseUrl = builder.Configuration["FlaskApi:BaseUrl"];
    client.BaseAddress = new Uri(baseUrl!);
});


// DI
builder.Services.AddScoped<ISignRecognitionService, SignRecognitionService>();
builder.Services.AddScoped<IUserService, UserService>();
builder.Services.AddScoped<IJwtTokenService, JwtTokenService>();
builder.Services.AddScoped<IPasswordHasher<User>, PasswordHasher<User>>();
builder.Services.AddControllers();

builder.WebHost.ConfigureKestrel(serverOptions =>
{
    serverOptions.ListenAnyIP(5268); 
});

var app = builder.Build();

app.UseSwagger();
app.UseSwaggerUI();

app.UseAuthentication();
app.UseAuthorization();

app.MapControllers();

app.Run();
