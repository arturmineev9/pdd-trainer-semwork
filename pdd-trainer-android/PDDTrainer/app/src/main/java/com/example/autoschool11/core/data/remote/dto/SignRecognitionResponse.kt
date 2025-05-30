package com.example.autoschool11.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SignRecognitionResponse(
    @SerializedName("class_id")
    val classId: Int
)
