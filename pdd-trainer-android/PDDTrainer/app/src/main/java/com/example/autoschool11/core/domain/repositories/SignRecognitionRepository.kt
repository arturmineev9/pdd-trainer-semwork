package com.example.autoschool11.core.domain.repositories

import com.example.autoschool11.core.data.remote.dto.SignRecognitionResponse
import com.example.autoschool11.core.domain.models.RoadSignModel
import java.io.File

interface SignRecognitionRepository {
    suspend fun getSignInfo(code: Int): RoadSignModel?
    suspend fun recognize(imageFile: File): Result<SignRecognitionResponse>
}
