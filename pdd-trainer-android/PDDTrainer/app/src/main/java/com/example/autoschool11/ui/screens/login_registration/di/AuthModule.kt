package com.example.autoschool11.ui.screens.login_registration.di

import com.example.autoschool11.core.domain.repositories.AuthRepository
import com.example.autoschool11.core.domain.usecases.LoginUseCase
import com.example.autoschool11.core.domain.usecases.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase = LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase = RegisterUseCase(repository)
} 