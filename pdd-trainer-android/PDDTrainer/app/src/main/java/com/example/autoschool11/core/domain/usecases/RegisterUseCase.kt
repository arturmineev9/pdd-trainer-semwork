package com.example.autoschool11.core.domain.usecases

import com.example.autoschool11.core.domain.repositories.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(firstName: String, email: String, password: String): Result<String> {
        return repository.register(firstName, email, password)
    }
} 
