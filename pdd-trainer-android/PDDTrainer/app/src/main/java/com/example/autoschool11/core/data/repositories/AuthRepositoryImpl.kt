package com.example.autoschool11.core.data.repositories

import com.example.autoschool11.core.data.remote.api.AuthApi
import com.example.autoschool11.core.data.remote.dto.LoginRequestDto
import com.example.autoschool11.core.data.remote.dto.RegisterRequestDto
import com.example.autoschool11.core.domain.repositories.AuthRepository
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {

    override suspend fun register(firstName: String, email: String, password: String): Result<String> {
        return try {
            val response = api.register(RegisterRequestDto(firstName, email, password))
            Result.success(response.token)
        } catch (e: HttpException) {
            val errorMessage = extractErrorMessage(e)
            Result.failure(Exception(errorMessage))
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Нет подключения к серверу"))
        } catch (e: SocketTimeoutException) {
            Result.failure(Exception("Сервер не отвечает"))
        } catch (e: Exception) {
            Result.failure(Exception("Произошла ошибка: ${e.localizedMessage}"))
        }
    }

    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val response = api.login(LoginRequestDto(email, password))
            Result.success(response.token)
        } catch (e: HttpException) {
            val errorMessage = extractErrorMessage(e)
            Result.failure(Exception(errorMessage))
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Нет подключения к серверу"))
        } catch (e: SocketTimeoutException) {
            Result.failure(Exception("Сервер не отвечает"))
        } catch (e: Exception) {
            Result.failure(Exception("Произошла ошибка: ${e.localizedMessage}"))
        }
    }

    private fun extractErrorMessage(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            val json = JSONObject(errorBody ?: "")
            json.optString("error", "Ошибка сервера: ${e.code()}")
        } catch (_: Exception) {
            "Ошибка сервера: ${e.code()}"
        }
    }
}

