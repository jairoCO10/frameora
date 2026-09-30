package com.jairoco.frameora.domain.editor.model

data class TextElement(
    val id: String,
    val text: String,
    val x: Float = 0.5f,
    val y: Float = 0.5f,
    val fontSize: Float = 32f,
    val bold: Boolean = false
)