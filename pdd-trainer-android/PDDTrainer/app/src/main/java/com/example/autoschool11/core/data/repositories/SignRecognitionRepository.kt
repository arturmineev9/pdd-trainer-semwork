package com.example.autoschool11.core.data.repositories

import android.content.Context
import com.example.autoschool11.core.data.local.dao.RoadSignRecognitionDao
import com.example.autoschool11.core.data.local.mapper.toRoadSignModel
import com.example.autoschool11.core.data.remote.api.SignRecognitionApi
import com.example.autoschool11.core.data.remote.dto.SignRecognitionResponse
import com.example.autoschool11.core.domain.models.RoadSignModel
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SignRecognitionRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: SignRecognitionApi,
    private val roadSignsDao: RoadSignRecognitionDao
) {

    suspend fun getSignInfo(code: Int): RoadSignModel? {
        val entity = roadSignsDao.getSignByCode(code)
        return entity?.toRoadSignModel(context)
    }

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
