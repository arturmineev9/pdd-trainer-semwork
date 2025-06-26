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
