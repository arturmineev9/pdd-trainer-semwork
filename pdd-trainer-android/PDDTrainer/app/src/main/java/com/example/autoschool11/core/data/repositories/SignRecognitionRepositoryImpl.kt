package com.example.autoschool11.core.data.repositories

import android.content.Context
import com.example.autoschool11.core.data.local.dao.RoadSignRecognitionDao
import com.example.autoschool11.core.data.local.mapper.toRoadSignModel
import com.example.autoschool11.core.data.remote.api.SignRecognitionApi
import com.example.autoschool11.core.data.remote.dto.SignRecognitionResponse
import com.example.autoschool11.core.domain.models.RoadSignModel
import com.example.autoschool11.core.domain.repositories.SignRecognitionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class SignRecognitionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: SignRecognitionApi,
    private val roadSignsDao: RoadSignRecognitionDao
) : SignRecognitionRepository {

    override suspend fun getSignInfo(code: Int): RoadSignModel? {
        val entity = roadSignsDao.getSignByCode(code)
        return entity?.toRoadSignModel(context)
    }

    override suspend fun recognize(imageFile: File): Result<SignRecognitionResponse> {
        return try {
            val requestFile = imageFile
                .asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)

            val response = api.uploadSignImage(body)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Пустой ответ от сервера"))
                }
            } else {
                // Обработка ошибок от сервера (например, 400, 500 и т.д.)
                val errorMessage = when (response.code()) {
                    400 -> "Неверный запрос"
                    404 -> "Сервис не найден"
                    500 -> "Внутренняя ошибка сервера"
                    else -> "Ошибка: ${response.code()}"
                }
                Result.failure(Exception(errorMessage))
            }

        } catch (e: UnknownHostException) {
            Result.failure(Exception("Нет подключения к серверу"))
        } catch (e: SocketTimeoutException) {
            Result.failure(Exception("Время ожидания сервера истекло"))
        } catch (e: IOException) {
            Result.failure(Exception("Ошибка соединения: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Result.failure(Exception("Неизвестная ошибка: ${e.localizedMessage}"))
        }
    }

}
