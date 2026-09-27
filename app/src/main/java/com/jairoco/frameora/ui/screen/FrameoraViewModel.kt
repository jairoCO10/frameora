package com.jairoco.frameora.ui.screen

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jairoco.frameora.domain.metadata.ExifReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FrameoraViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val exifReader = ExifReader(application)

    private val _uiState = MutableStateFlow(
        FrameoraUiState()
    )

    val uiState: StateFlow<FrameoraUiState> =
        _uiState.asStateFlow()

    fun selectPhoto(uri: Uri?) {

        if (uri == null) {
            return
        }

        _uiState.value = _uiState.value.copy(
            selectedImageUri = uri,
            photoMetadata = null,
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch(Dispatchers.IO) {

            try {

                val metadata = exifReader.read(uri)

                _uiState.value = _uiState.value.copy(
                    photoMetadata = metadata,
                    isLoading = false
                )

            } catch (exception: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "No se pudieron leer los metadatos."
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }
}