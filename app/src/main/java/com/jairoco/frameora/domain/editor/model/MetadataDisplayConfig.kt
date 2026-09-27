package com.jairoco.frameora.domain.editor.model

data class MetadataDisplayConfig(
    val showMake: Boolean = true,
    val showModel: Boolean = true,
    val showFocalLength: Boolean = true,
    val showAperture: Boolean = true,
    val showExposureTime: Boolean = true,
    val showIso: Boolean = true,
    val showDate: Boolean = true,

    val showLens: Boolean = false,
    val showFocalLength35mm: Boolean = false,
    val showDigitalZoom: Boolean = false,
    val showGps: Boolean = false,
    val showFlash: Boolean = false
)