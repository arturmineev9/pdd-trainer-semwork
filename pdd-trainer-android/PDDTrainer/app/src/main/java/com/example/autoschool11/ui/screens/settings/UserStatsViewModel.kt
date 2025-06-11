package com.example.autoschool11.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoschool11.core.domain.usecases.LoadStatsUseCase
import com.example.autoschool11.core.domain.usecases.SaveStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UserStatsState {
    object Idle : UserStatsState()
    object Loading : UserStatsState()
    object Success : UserStatsState()
    data class Error(val message: String) : UserStatsState()
}

@HiltViewModel
class UserStatsViewModel @Inject constructor(
    private val saveStatsUseCase: SaveStatsUseCase,
    private val loadStatsUseCase: LoadStatsUseCase
) : ViewModel() {
    private val _userStatsState = MutableStateFlow<UserStatsState>(UserStatsState.Idle)
    val userStatsState: StateFlow<UserStatsState> = _userStatsState

    fun saveStats() {
        _userStatsState.value = UserStatsState.Loading
        viewModelScope.launch {
            try {
                saveStatsUseCase()
                _userStatsState.value = UserStatsState.Success
            } catch (e: Exception) {
                _userStatsState.value = UserStatsState.Error(e.message ?: "Ошибка сохранения")
            }
        }
    }

    fun loadStats() {
        _userStatsState.value = UserStatsState.Loading
        viewModelScope.launch {
            try {
                loadStatsUseCase()
                _userStatsState.value = UserStatsState.Success
            } catch (e: Exception) {
                _userStatsState.value = UserStatsState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }
    
    fun resetState() {
        _userStatsState.value = UserStatsState.Idle
    }
} 
