package com.example.autoschool11.core.domain.usecases

import com.example.autoschool11.core.domain.repositories.UserStatsRepository
import javax.inject.Inject

class LoadStatsUseCase @Inject constructor(private val repository: UserStatsRepository) {
    suspend operator fun invoke() = repository.loadStatsFromServer()
} 