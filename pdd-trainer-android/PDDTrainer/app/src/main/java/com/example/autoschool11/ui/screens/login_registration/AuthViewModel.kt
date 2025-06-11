package com.example.autoschool11.ui.screens.login_registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoschool11.core.domain.usecases.LoginUseCase
import com.example.autoschool11.core.domain.usecases.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val token = loginUseCase(email, password)
                _authState.value = AuthState.Success(token)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Ошибка авторизации")
            }
        }
    }

    fun register(firstName: String, email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val token = registerUseCase(firstName, email, password)
                _authState.value = AuthState.Success(token)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Ошибка регистрации")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
} 