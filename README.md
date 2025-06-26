# 🚗 PDD Trainer - Тренажер ПДД

Современное приложение для подготовки к экзамену по Правилам Дорожного Движения с поддержкой синхронизации прогресса между устройствами.

## 📱 О проекте

PDD Trainer - это полнофункциональное приложение для изучения ПДД, которое включает в себя:
- **40 вариантов экзамена** по 20 вопросов каждый
- **Распознавание дорожных знаков** с помощью камеры
- **Синхронизация прогресса** между устройствами
- **Современный UI/UX** с поддержкой тем
- **Авторизация пользователей** через JWT

## 🏗️ Архитектура

### Backend (.NET)
- **Clean Architecture** - разделение на слои Domain, Application, Infrastructure
- **ASP.NET Core 8** с Web API
- **Entity Framework Core** для работы с PostgreSQL
- **JWT Authentication** для безопасной авторизации
- **xUnit + Moq** для тестирования

### Android
- **MVVM + Clean Architecture** 
- **Kotlin** для современной разработки
- **Hilt** для dependency injection
- **Retrofit** для работы с API
- **Room** (планируется) для локальной базы данных

## 🚀 Технологии

### Backend
![.NET](https://img.shields.io/badge/.NET-8.0-512BD4?style=for-the-badge&logo=.net&logoColor=white)
![C#](https://img.shields.io/badge/C%23-239120?style=for-the-badge&logo=c-sharp&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Entity Framework](https://img.shields.io/badge/Entity_Framework-512BD4?style=for-the-badge&logo=.net&logoColor=white)

### Android
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Hilt](https://img.shields.io/badge/Hilt-4285F4?style=for-the-badge&logo=google&logoColor=white)
![Retrofit](https://img.shields.io/badge/Retrofit-4285F4?style=for-the-badge&logo=google&logoColor=white)

## 📸 Скриншоты

<div align="center">
  <img src="https://via.placeholder.com/300x600/4CAF50/FFFFFF?text=Главный+экран" alt="Главный экран" width="150"/>
  <img src="https://via.placeholder.com/300x600/2196F3/FFFFFF?text=Экзамен" alt="Экзамен" width="150"/>
  <img src="https://via.placeholder.com/300x600/FF9800/FFFFFF?text=Распознавание+знаков" alt="Распознавание знаков" width="150"/>
  <img src="https://via.placeholder.com/300x600/9C27B0/FFFFFF?text=Настройки" alt="Настройки" width="150"/>
</div>

## 🛠️ Установка и запуск

### Backend (.NET)

1. **Клонируйте репозиторий**
```bash
git clone https://github.com/your-username/pdd-trainer-semwork.git
cd pdd-trainer-semwork/pdd-trainer-dotnet/PddTrainer
```

2. **Настройте базу данных**
```bash
# Установите PostgreSQL и создайте базу данных
# Обновите строку подключения в appsettings.json
```

3. **Настройте User Secrets**
```bash
dotnet user-secrets set "JwtSettings:Key" "your-super-secret-key-here"
```

4. **Запустите приложение**
```bash
dotnet run
```

### Android

1. **Откройте проект в Android Studio**
```bash
cd pdd-trainer-android/PDDTrainer
```

2. **Настройте API endpoint**
```kotlin
// В NetworkModule.kt обновите базовый URL
fun provideBaseUrl(): String = "http://your-server-ip:5268/"
```

3. **Соберите и запустите**
```bash
./gradlew assembleDebug
```

## 🧪 Тестирование

### Backend тесты
```bash
cd pdd-trainer-dotnet/PddTrainer.Tests
dotnet test
```

## 📁 Структура проекта

```
pdd-trainer-semwork/
├── pdd-trainer-dotnet/          # Backend (.NET)
│   ├── PddTrainer/
│   │   ├── Domain/              # Бизнес-логика
│   │   ├── Infrastructure/      # Внешние зависимости
│   │   └── Web/                 # API контроллеры
│   └── PddTrainer.Tests/        # Unit тесты
├── pdd-trainer-android/         # Android приложение
│   └── PDDTrainer/
│       ├── app/src/main/java/
│       │   └── com/example/autoschool11/
│       │       ├── core/        # Clean Architecture
│       │       └── ui/          # Пользовательский интерфейс
└── README.md
```

## 🔐 Безопасность

- **JWT токены** для аутентификации
- **Хеширование паролей** с помощью ASP.NET Core Identity
- **User Secrets** для хранения конфиденциальных данных
- **Валидация данных** на клиенте и сервере

## 🤝 Вклад в проект

1. Fork репозитория
2. Создайте feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit изменения (`git commit -m 'Add some AmazingFeature'`)
4. Push в branch (`git push origin feature/AmazingFeature`)
5. Откройте Pull Request

## 📄 Лицензия

Этот проект распространяется под лицензией MIT. Смотрите файл `LICENSE` для получения дополнительной информации.

## 📞 Контакты

Если у вас есть вопросы или предложения, создайте [Issue](https://github.com/your-username/pdd-trainer-semwork/issues) в репозитории.

---

<div align="center">
  <p>Сделано с ❤️ для изучения ПДД</p>
  <p>⭐ Не забудьте поставить звездочку, если проект вам понравился!</p>
</div> 
