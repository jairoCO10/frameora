package com.jairoco.frameora.domain.editor.model

import androidx.compose.ui.graphics.Color

data class MetadataStyle(
    val backgroundColor: Color = Color.White,

    val primaryTextColor: Color = Color.Black,
    val secondaryTextColor: Color = Color.DarkGray,
    val dateTextColor: Color = Color.Gray,

    val manufacturerSize: Float = 17f,
    val modelSize: Float = 14f,
    val metadataSize: Float = 14f,
    val secondarySize: Float = 13f,

    val horizontalPadding: Float = 24f,
    val verticalPadding: Float = 16f,

    val alignment: MetadataAlignment = MetadataAlignment.START
)

enum class MetadataAlignment {
    START,
    CENTER,
    END
}