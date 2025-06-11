package com.example.autoschool11.ui.screens.settings.di

import com.example.autoschool11.core.data.repositories.UserStatsRepositoryImpl
import com.example.autoschool11.core.domain.repositories.UserStatsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserStatsModule {
    @Binds
    @Singleton
    abstract fun bindUserStatsRepository(impl: UserStatsRepositoryImpl): UserStatsRepository
} 
