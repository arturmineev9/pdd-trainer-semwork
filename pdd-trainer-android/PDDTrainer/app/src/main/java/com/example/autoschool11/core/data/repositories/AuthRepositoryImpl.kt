package com.example.autoschool11.core.data.repositories

import com.example.autoschool11.core.data.remote.api.AuthApi
import com.example.autoschool11.core.data.remote.dto.LoginRequestDto
import com.example.autoschool11.core.data.remote.dto.RegisterRequestDto
import com.example.autoschool11.core.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun register(firstName: String, email: String, password: String): String {
        val response = api.register(RegisterRequestDto(firstName, email, password))
        return response.token
    }

    override suspend fun login(email: String, password: String): String {
        val response = api.login(LoginRequestDto(email, password))
        return response.token
    }
}
