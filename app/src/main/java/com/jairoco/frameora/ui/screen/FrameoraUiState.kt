package com.jairoco.frameora.ui.screen

import android.net.Uri
import com.jairoco.frameora.domain.model.PhotoMetadata

data class FrameoraUiState(
    val selectedImageUri: Uri? = null,
    val photoMetadata: PhotoMetadata? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)