package com.example.autoschool11.core.data.remote.api

import com.example.autoschool11.core.data.remote.dto.AuthResponseDto
import com.example.autoschool11.core.data.remote.dto.LoginRequestDto
import com.example.autoschool11.core.data.remote.dto.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequestDto): AuthResponseDto

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequestDto): AuthResponseDto
}
