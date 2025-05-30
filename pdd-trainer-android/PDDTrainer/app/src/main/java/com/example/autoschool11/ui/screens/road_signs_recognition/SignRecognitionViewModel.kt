package com.example.autoschool11.ui.screens.road_signs_recognition

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoschool11.core.data.SignRecognitionRepository
import com.example.autoschool11.core.utils.toFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignRecognitionViewModel @Inject constructor(
    private val repository: SignRecognitionRepository
) : ViewModel() {

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri = _selectedImageUri.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()


    fun onImageSelected(uri: Uri?) {
        _selectedImageUri.value = uri

    }

    fun onRecognizeButtonClicked(context: Context) {
        viewModelScope.launch {
            try {
                val imageUri = _selectedImageUri.value
                val imageFile = if (imageUri != null && imageUri.scheme != "https") {
                    imageUri.toFile(context)
                } else {
                    null
                }
                if (imageFile == null) throw Exception("Изображение не выбрано")

                repository.recognize(imageFile)
            } catch (e: Exception) {
                _error.value = e.message ?: "Ошибка на шаге 2 регистрации"
            }
        }
    }
}
