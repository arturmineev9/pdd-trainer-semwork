package com.example.autoschool11.core.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "road_signs")
data class RoadSignRecognitionEntity(
    @PrimaryKey
    @ColumnInfo(name = "code")
    val code: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String
)
