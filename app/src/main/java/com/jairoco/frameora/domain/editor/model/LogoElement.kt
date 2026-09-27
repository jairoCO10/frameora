package com.jairoco.frameora.domain.editor.model

import android.net.Uri

data class LogoElement(
    val uri: Uri? = null,
    val x: Float = 0f,
    val y: Float = 0f,
    val scale: Float = 1f
)