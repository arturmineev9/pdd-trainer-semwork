package com.example.autoschool11.core.domain.usecases

import com.example.autoschool11.core.domain.repositories.TokenRepository
import javax.inject.Inject

class ClearTokenUseCase @Inject constructor(private val repository: TokenRepository) {
    operator fun invoke() = repository.clearToken()
} 