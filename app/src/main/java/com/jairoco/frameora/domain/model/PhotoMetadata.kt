package com.jairoco.frameora.domain.model

data class PhotoMetadata(

    // Archivo
    val fileName: String? = null,
    val mimeType: String? = null,

    // Imagen
    val width: Int? = null,
    val height: Int? = null,

    // Cámara
    val make: String? = null,
    val model: String? = null,
    val software: String? = null,

    // Fechas
    val dateTime: String? = null,
    val dateTimeOriginal: String? = null,
    val dateTimeDigitized: String? = null,

    // Exposición
    val iso: Int? = null,
    val aperture: Double? = null,
    val exposureTime: Double? = null,
    val exposureBias: Double? = null,
    val exposureProgram: Int? = null,

    // Óptica
    val focalLength: Double? = null,
    val focalLength35mm: Int? = null,
    val digitalZoom: Double? = null,
    val lensMake: String? = null,
    val lensModel: String? = null,

    // Flash
    val flash: Int? = null,
    val flashFired: Boolean? = null,

    // Color
    val whiteBalance: Int? = null,
    val colorSpace: Int? = null,

    // Medición
    val meteringMode: Int? = null,

    // Orientación
    val orientation: Int? = null,

    // GPS
    val latitude: Double? = null,
    val longitude: Double? = null,
    val gpsDate: String? = null,
    val gpsTime: String? = null
)