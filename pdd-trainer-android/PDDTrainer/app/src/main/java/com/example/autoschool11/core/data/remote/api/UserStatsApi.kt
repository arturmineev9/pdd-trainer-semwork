package com.example.autoschool11.core.data.remote.api

import com.example.autoschool11.core.data.remote.dto.UpdateUserStatsRequest
import com.example.autoschool11.core.data.remote.dto.UserStatsDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserStatsApi {
    @GET("api/user-stats")
    suspend fun getStats(): List<UserStatsDto>

    @POST("api/user-stats")
    suspend fun saveStats(@Body request: UpdateUserStatsRequest)
}
