package com.example.autoschool11.core.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.autoschool11.core.data.local.RoadSignsRecognitionDatabase
import com.example.autoschool11.core.data.local.dao.RoadSignRecognitionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): RoadSignsRecognitionDatabase {
        return Room.databaseBuilder(
            context,
            RoadSignsRecognitionDatabase::class.java,
            "road_signs_recognition"
        )
            .createFromAsset("road_signs_recognition.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: RoadSignsRecognitionDatabase): RoadSignRecognitionDao = db.roadSignDao
}
