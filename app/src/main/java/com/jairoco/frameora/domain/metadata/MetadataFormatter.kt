package com.jairoco.frameora.domain.metadata

import com.jairoco.frameora.domain.model.PhotoMetadata
import java.util.Locale
import kotlin.math.roundToInt

object MetadataFormatter {

    fun formatFocalLength(value: Double?): String {
        if (value == null) return "--"

        return if (value % 1.0 == 0.0) {
            "${value.toInt()} mm"
        } else {
            String.format(Locale.US, "%.1f mm", value)
        }
    }

    fun formatAperture(value: Double?): String {
        if (value == null) return "--"

        return if (value % 1.0 == 0.0) {
            "f/${value.toInt()}"
        } else {
            String.format(Locale.US, "f/%.1f", value)
        }
    }

    fun formatExposureTime(value: Double?): String {
        if (value == null || value <= 0.0) return "--"

        if (value < 1.0) {
            val denominator = (1.0 / value).roundToInt()
            return "1/$denominator s"
        }

        return if (value % 1.0 == 0.0) {
            "${value.toInt()} s"
        } else {
            String.format(Locale.US, "%.1f s", value)
        }
    }

    fun formatIso(value: Int?): String {
        return value?.let { "ISO $it" } ?: "ISO --"
    }

    fun formatExposureBias(value: Double?): String {
        if (value == null) return "--"

        return when {
            value > 0 -> {
                String.format(Locale.US, "+%.1f EV", value)
            }

            value < 0 -> {
                String.format(Locale.US, "%.1f EV", value)
            }

            else -> {
                "0 EV"
            }
        }
    }

    fun formatDigitalZoom(value: Double?): String {
        if (value == null || value <= 0.0) return "--"

        return if (value % 1.0 == 0.0) {
            "${value.toInt()}x"
        } else {
            String.format(Locale.US, "%.1fx", value)
        }
    }

    fun formatFlash(value: Int?): String {
        if (value == null) return "--"

        return when {
            value and 0x01 != 0 -> "Disparado"
            else -> "No disparado"
        }
    }

    fun formatWhiteBalance(value: Int?): String {
        if (value == null) return "--"

        return when (value) {
            0 -> "Automático"
            1 -> "Manual"
            else -> value.toString()
        }
    }

    fun formatGps(
        latitude: Double?,
        longitude: Double?
    ): String {

        if (latitude == null || longitude == null) {
            return "--"
        }

        return String.format(
            Locale.US,
            "%.6f, %.6f",
            latitude,
            longitude
        )
    }

    fun formatDateTime(value: String?): String {
        if (value.isNullOrBlank()) return "--"

        return try {

            val parts = value.split(" ")

            if (parts.size < 2) {
                return value
            }

            val date = parts[0]
            val time = parts[1]

            val dateParts = date.split(":")

            if (dateParts.size != 3) {
                return value
            }

            val year = dateParts[0]
            val month = dateParts[1]
            val day = dateParts[2]

            val cleanTime = time.substringBeforeLast(":")

            "$day/$month/$year $cleanTime"

        } catch (exception: Exception) {
            value
        }
    }

    fun formatMetadata(metadata: PhotoMetadata): String {

        val camera =
            listOfNotNull(
                metadata.make?.takeIf { it.isNotBlank() },
                metadata.model?.takeIf { it.isNotBlank() }
            ).joinToString(" ")

        val focal =
            formatFocalLength(metadata.focalLength)

        val aperture =
            formatAperture(metadata.aperture)

        val exposure =
            formatExposureTime(metadata.exposureTime)

        val iso =
            formatIso(metadata.iso)

        return buildString {

            if (camera.isNotBlank()) {
                appendLine(camera)
            }

            appendLine()

            append(
                "$focal   $aperture   $exposure   $iso"
            )

            metadata.dateTimeOriginal
                ?.takeIf { it.isNotBlank() }
                ?.let {

                    appendLine()
                    appendLine()

                    append(
                        formatDateTime(it)
                    )
                }
        }
    }
}