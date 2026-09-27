package com.jairoco.frameora.ui.editor

import android.net.Uri
import com.jairoco.frameora.domain.editor.model.PhotoEdit
import com.jairoco.frameora.domain.model.PhotoMetadata

data class EditorUiState(
    val originalUri: Uri? = null,
    val metadata: PhotoMetadata? = null,
    val edit: PhotoEdit = PhotoEdit(),
    val isExporting: Boolean = false,
    val errorMessage: String? = null
)