package com.example.autoschool11.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UpdateUserStatsRequest(
    @SerializedName("stats")
    val stats: List<UserStatsDto>
) 