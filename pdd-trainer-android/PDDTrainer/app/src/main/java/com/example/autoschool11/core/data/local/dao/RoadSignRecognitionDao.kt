package com.example.autoschool11.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.autoschool11.core.data.local.entities.RoadSignRecognitionEntity


@Dao
interface RoadSignRecognitionDao {

    @Query("SELECT * FROM road_signs WHERE code = :code")
    suspend fun getSignByCode(code: Int): RoadSignRecognitionEntity?
}
