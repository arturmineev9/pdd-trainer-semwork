package com.example.autoschool11.core.data

import com.example.autoschool11.core.data.remote.SignRecognitionApi
import com.example.autoschool11.core.data.remote.dto.SignRecognitionResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SignRecognitionRepository @Inject constructor(
    private val api: SignRecognitionApi
) {
    suspend fun recognize(imageFile: File): Result<SignRecognitionResponse> {
        return try {
            val requestFile = imageFile
                .asRequestBody("image/jpeg".toMediaTypeOrNull())

            val body = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)

            val response = api.uploadSignImage(body)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
