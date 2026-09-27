package com.jairoco.frameora.ui.editor

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.jairoco.frameora.domain.editor.model.FrameConfig
import com.jairoco.frameora.domain.model.PhotoMetadata
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.jairoco.frameora.domain.editor.model.TextElement
import com.jairoco.frameora.domain.editor.model.MetadataField

class EditorViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(
        EditorUiState()
    )

    val uiState: StateFlow<EditorUiState> =
        _uiState.asStateFlow()

    fun setPhoto(
        uri: Uri,
        metadata: PhotoMetadata? = null
    ) {
        _uiState.value = _uiState.value.copy(
            originalUri = uri,
            metadata = metadata
        )
    }
    fun addText(text: String) {

        val currentTexts = _uiState.value.edit.texts

        val newText = TextElement(
            text = text,
            x = 40f,
            y = 40f,
            fontSize = 32f
        )

        _uiState.value = _uiState.value.copy(
            edit = _uiState.value.edit.copy(
                texts = currentTexts + newText
            )
        )
    }

    fun setFrameEnabled(enabled: Boolean) {

        val currentFrame =
            _uiState.value.edit.frame
                ?: FrameConfig()

        _uiState.value = _uiState.value.copy(
            edit = _uiState.value.edit.copy(
                frame = currentFrame.copy(
                    enabled = enabled
                )
            )
        )
    }

    fun setFrameThickness(thickness: Float) {

        val currentFrame =
            _uiState.value.edit.frame
                ?: FrameConfig()

        _uiState.value = _uiState.value.copy(
            edit = _uiState.value.edit.copy(
                frame = currentFrame.copy(
                    thickness = thickness,
                    enabled = true
                )
            )
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }

    fun setMetadataField(
        field: MetadataField,
        enabled: Boolean
    ) {
        val current = _uiState.value.edit.metadata

        val updated = when (field) {

            MetadataField.MAKE ->
                current.copy(showMake = enabled)

            MetadataField.MODEL ->
                current.copy(showModel = enabled)

            MetadataField.FOCAL_LENGTH ->
                current.copy(showFocalLength = enabled)

            MetadataField.APERTURE ->
                current.copy(showAperture = enabled)

            MetadataField.EXPOSURE_TIME ->
                current.copy(showExposureTime = enabled)

            MetadataField.ISO ->
                current.copy(showIso = enabled)

            MetadataField.DATE ->
                current.copy(showDate = enabled)

            MetadataField.LENS ->
                current.copy(showLens = enabled)

            MetadataField.FOCAL_LENGTH_35MM ->
                current.copy(showFocalLength35mm = enabled)

            MetadataField.DIGITAL_ZOOM ->
                current.copy(showDigitalZoom = enabled)

            MetadataField.GPS ->
                current.copy(showGps = enabled)

            MetadataField.FLASH ->
                current.copy(showFlash = enabled)
        }

        _uiState.value = _uiState.value.copy(
            edit = _uiState.value.edit.copy(
                metadata = updated
            )
        )
    }
}