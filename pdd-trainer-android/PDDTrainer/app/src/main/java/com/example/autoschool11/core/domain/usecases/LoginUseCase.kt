package com.example.autoschool11.core.domain.usecases

import com.example.autoschool11.core.domain.repositories.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return repository.login(email, password)
    }
} 
