package com.jairoco.frameora.domain.brand

data class CameraBrand(
    val id: String,
    val name: String,
    val aliases: List<String>,
    val logoAsset: String? = null
)