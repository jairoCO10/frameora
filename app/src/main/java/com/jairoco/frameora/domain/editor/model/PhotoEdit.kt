package com.jairoco.frameora.domain.editor.model

data class PhotoEdit(
    val frame: FrameConfig? = null,
    val texts: List<TextElement> = emptyList(),
    val logo: LogoElement? = null,
    val metadata: MetadataDisplayConfig = MetadataDisplayConfig()
)