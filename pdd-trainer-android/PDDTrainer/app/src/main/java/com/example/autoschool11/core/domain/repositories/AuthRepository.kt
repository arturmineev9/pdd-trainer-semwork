package com.example.autoschool11.core.domain.repositories

interface AuthRepository {
    suspend fun register(firstName: String, email: String, password: String):  Result<String>
    suspend fun login(email: String, password: String):  Result<String>
}
