package com.example.autoschool11.core.domain.usecases

import com.example.autoschool11.core.domain.models.RoadSignModel
import com.example.autoschool11.core.domain.repositories.SignRecognitionRepository
import com.example.autoschool11.core.domain.repositories.UserStatsRepository
import javax.inject.Inject

class GetSignInfoUseCase @Inject constructor(private val repository: SignRecognitionRepository) {
    suspend operator fun invoke(code: Int) : RoadSignModel? = repository.getSignInfo(code)
}
