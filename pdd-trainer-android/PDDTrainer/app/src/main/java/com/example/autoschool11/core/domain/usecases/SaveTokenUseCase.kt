package com.example.autoschool11.core.domain.usecases

import com.example.autoschool11.core.domain.repositories.TokenRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val repository: TokenRepository) {
    operator fun invoke(token: String) = repository.saveToken(token)
} 