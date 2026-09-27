package com.jairoco.frameora.domain.metadata

import android.content.Context
import android.net.Uri
import androidx.exifinterface.media.ExifInterface

class ExifInspector(
    private val context: Context
) {

    fun inspect(uri: Uri): Map<String, String> {

        val inputStream = context.contentResolver
            .openInputStream(uri)
            ?: throw IllegalArgumentException("No se pudo abrir la imagen")

        inputStream.use { stream ->

            val exif = ExifInterface(stream)

            val result = linkedMapOf<String, String>()

            add(result, "Make", exif.getAttribute(ExifInterface.TAG_MAKE))
            add(result, "Model", exif.getAttribute(ExifInterface.TAG_MODEL))
            add(result, "Software", exif.getAttribute(ExifInterface.TAG_SOFTWARE))

            add(
                result,
                "ImageWidth",
                exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)
            )

            add(
                result,
                "ImageLength",
                exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)
            )

            add(
                result,
                "Orientation",
                exif.getAttribute(ExifInterface.TAG_ORIENTATION)
            )

            add(
                result,
                "DateTime",
                exif.getAttribute(ExifInterface.TAG_DATETIME)
            )

            add(
                result,
                "DateTimeOriginal",
                exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
            )

            add(
                result,
                "DateTimeDigitized",
                exif.getAttribute(ExifInterface.TAG_DATETIME_DIGITIZED)
            )

            add(
                result,
                "ISO",
                exif.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS)
            )

            add(
                result,
                "ExposureTime",
                exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME)
            )

            add(
                result,
                "FNumber",
                exif.getAttribute(ExifInterface.TAG_F_NUMBER)
            )

            add(
                result,
                "ExposureBiasValue",
                exif.getAttribute(ExifInterface.TAG_EXPOSURE_BIAS_VALUE)
            )

            add(
                result,
                "ExposureProgram",
                exif.getAttribute(ExifInterface.TAG_EXPOSURE_PROGRAM)
            )

            add(
                result,
                "FocalLength",
                exif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH)
            )

            add(
                result,
                "FocalLengthIn35mmFilm",
                exif.getAttribute("FocalLengthIn35mmFilm")
            )

            add(
                result,
                "DigitalZoomRatio",
                exif.getAttribute(ExifInterface.TAG_DIGITAL_ZOOM_RATIO)
            )

            add(
                result,
                "LensMake",
                exif.getAttribute(ExifInterface.TAG_LENS_MAKE)
            )

            add(
                result,
                "LensModel",
                exif.getAttribute(ExifInterface.TAG_LENS_MODEL)
            )

            add(
                result,
                "Flash",
                exif.getAttribute(ExifInterface.TAG_FLASH)
            )

            add(
                result,
                "WhiteBalance",
                exif.getAttribute(ExifInterface.TAG_WHITE_BALANCE)
            )

            add(
                result,
                "MeteringMode",
                exif.getAttribute(ExifInterface.TAG_METERING_MODE)
            )

            add(
                result,
                "ColorSpace",
                exif.getAttribute(ExifInterface.TAG_COLOR_SPACE)
            )

            add(
                result,
                "GPSLatitude",
                exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
            )

            add(
                result,
                "GPSLatitudeRef",
                exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)
            )

            add(
                result,
                "GPSLongitude",
                exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
            )

            add(
                result,
                "GPSLongitudeRef",
                exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)
            )

            add(
                result,
                "GPSDateStamp",
                exif.getAttribute(ExifInterface.TAG_GPS_DATESTAMP)
            )

            add(
                result,
                "GPSTimeStamp",
                exif.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP)
            )

            /*
             * XMP
             */
            add(
                result,
                "XMP",
                exif.getAttribute(ExifInterface.TAG_XMP)
            )

            return result
        }
    }

    private fun add(
        result: MutableMap<String, String>,
        key: String,
        value: String?
    ) {
        if (!value.isNullOrBlank()) {
            result[key] = value
        }
    }
}