package com.example.autoschool11.core.domain.usecases

import com.example.autoschool11.core.data.remote.dto.SignRecognitionResponse
import com.example.autoschool11.core.domain.repositories.SignRecognitionRepository
import java.io.File
import javax.inject.Inject

class RecognizeRoadSignUseCase @Inject constructor(private val repository: SignRecognitionRepository) {
    suspend operator fun invoke(imageFile: File): Result<SignRecognitionResponse> = repository.recognize(imageFile)
}
