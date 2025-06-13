package com.example.autoschool11.core.data.di

import com.example.autoschool11.core.data.repositories.TokenRepositoryImpl
import com.example.autoschool11.core.data.repositories.AuthRepositoryImpl
import com.example.autoschool11.core.data.repositories.SignRecognitionRepositoryImpl
import com.example.autoschool11.core.data.repositories.UserStatsRepositoryImpl
import com.example.autoschool11.core.domain.repositories.AuthRepository
import com.example.autoschool11.core.domain.repositories.SignRecognitionRepository
import com.example.autoschool11.core.domain.repositories.TokenRepository
import com.example.autoschool11.core.domain.repositories.UserStatsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserStatsRepository(impl: UserStatsRepositoryImpl): UserStatsRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(impl: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    abstract fun bindSignRecognitionRepository(impl: SignRecognitionRepositoryImpl) : SignRecognitionRepository
} 
