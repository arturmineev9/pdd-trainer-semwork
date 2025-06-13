package com.example.autoschool11.ui.screens.login_registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.autoschool11.core.domain.usecases.LoginUseCase
import com.example.autoschool11.core.domain.usecases.RegisterUseCase
import com.example.autoschool11.core.domain.usecases.SaveTokenUseCase
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(loginUseCase, registerUseCase, saveTokenUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 
