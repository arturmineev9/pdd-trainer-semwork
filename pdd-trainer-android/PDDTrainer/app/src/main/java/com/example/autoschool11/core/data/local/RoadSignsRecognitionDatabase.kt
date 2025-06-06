package com.example.autoschool11.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.autoschool11.core.data.local.dao.RoadSignRecognitionDao
import com.example.autoschool11.core.data.local.entities.RoadSignRecognitionEntity

@Database(entities = [RoadSignRecognitionEntity::class], version = 2, exportSchema = false)
abstract class RoadSignsRecognitionDatabase : RoomDatabase() {
    abstract val roadSignDao: RoadSignRecognitionDao
}
