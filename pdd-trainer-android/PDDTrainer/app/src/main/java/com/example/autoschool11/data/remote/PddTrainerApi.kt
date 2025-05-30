package com.example.autoschool11.data.remote

import com.example.autoschool11.data.remote.dto.SignPredictionResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SignDetectionApi {
    @Multipart
    @POST("/predict")
    suspend fun detectSign(
        @Part image: MultipartBody.Part
    ): Response<SignPredictionResponse>
}
