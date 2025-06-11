package com.example.autoschool11.core.data.remote.api

import com.example.autoschool11.core.data.remote.dto.SignRecognitionResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SignRecognitionApi {
    @Multipart
    @POST("api/v1/sign-recognition")
    suspend fun uploadSignImage(
        @Part image: MultipartBody.Part
    ): Response<SignRecognitionResponse>
}
