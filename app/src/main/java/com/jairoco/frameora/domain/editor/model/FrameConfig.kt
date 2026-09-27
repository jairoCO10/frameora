package com.jairoco.frameora.domain.editor.model

import androidx.compose.ui.graphics.Color

data class FrameConfig(
    val enabled: Boolean = false,
    val thickness: Float = 40f,
    val color: Color = Color.White
)