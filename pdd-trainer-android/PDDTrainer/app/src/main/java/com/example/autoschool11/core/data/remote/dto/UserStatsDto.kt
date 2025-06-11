package com.example.autoschool11.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserStatsDto(
    @SerializedName("variantNumber")
    val variantNumber: Int,
    @SerializedName("correctAnswers")
    val correctAnswers: Int
) 