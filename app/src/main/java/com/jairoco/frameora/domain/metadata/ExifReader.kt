package com.jairoco.frameora.domain.metadata

import android.content.Context
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.jairoco.frameora.domain.model.PhotoMetadata

class ExifReader(
    private val context: Context
) {

    fun read(uri: Uri): PhotoMetadata {

        val inputStream = context.contentResolver
            .openInputStream(uri)
            ?: throw IllegalArgumentException("No se pudo abrir la imagen")

        inputStream.use { stream ->

            val exif = ExifInterface(stream)

            val latLong = exif.latLong

            /*
             * Flash
             *
             * No usamos getAttributeInt(..., 0) porque eso mezcla:
             *
             *   atributo inexistente -> 0
             *   atributo existente = 0 -> 0
             *
             * Necesitamos distinguir ambos casos.
             */
            val flashRaw = exif
                .getAttribute(ExifInterface.TAG_FLASH)
                ?.toIntOrNull()

            /*
             * Resolución
             */
            val width = exif
                .getAttributeInt(
                    ExifInterface.TAG_IMAGE_WIDTH,
                    0
                )
                .takeIf { it > 0 }

            val height = exif
                .getAttributeInt(
                    ExifInterface.TAG_IMAGE_LENGTH,
                    0
                )
                .takeIf { it > 0 }

            /*
             * Información básica de cámara
             */
            val make = exif.getAttribute(ExifInterface.TAG_MAKE)
            val model = exif.getAttribute(ExifInterface.TAG_MODEL)
            val software = exif.getAttribute(ExifInterface.TAG_SOFTWARE)

            /*
             * Fechas
             */
            val dateTime =
                exif.getAttribute(ExifInterface.TAG_DATETIME)

            val dateTimeOriginal =
                exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)

            val dateTimeDigitized =
                exif.getAttribute(ExifInterface.TAG_DATETIME_DIGITIZED)

            /*
             * Exposición
             */
            val iso = exif
                .getAttributeInt(
                    ExifInterface.TAG_ISO_SPEED_RATINGS,
                    0
                )
                .takeIf { it > 0 }

            val aperture = exif
                .getAttributeDouble(
                    ExifInterface.TAG_F_NUMBER,
                    0.0
                )
                .takeIf { it > 0 }

            val exposureTime = exif
                .getAttributeDouble(
                    ExifInterface.TAG_EXPOSURE_TIME,
                    0.0
                )
                .takeIf { it > 0 }

            val exposureBias = exif
                .getAttributeDouble(
                    ExifInterface.TAG_EXPOSURE_BIAS_VALUE,
                    0.0
                )

            val exposureProgram = exif
                .getAttributeInt(
                    ExifInterface.TAG_EXPOSURE_PROGRAM,
                    -1
                )
                .takeIf { it >= 0 }

            /*
             * Óptica
             */
            val focalLength = exif
                .getAttributeDouble(
                    ExifInterface.TAG_FOCAL_LENGTH,
                    0.0
                )
                .takeIf { it > 0 }

            /*
             * ExifInterface 1.4.2 no nos expone de forma
             * consistente TAG_FOCAL_LENGTH_35MM como constante.
             *
             * Utilizamos directamente el nombre EXIF.
             */
            val focalLength35mm = exif
                .getAttributeInt(
                    "FocalLengthIn35mmFilm",
                    0
                )
                .takeIf { it > 0 }

            val digitalZoom = exif
                .getAttributeDouble(
                    ExifInterface.TAG_DIGITAL_ZOOM_RATIO,
                    0.0
                )
                .takeIf { it > 0 }

            val lensMake =
                exif.getAttribute(ExifInterface.TAG_LENS_MAKE)

            val lensModel =
                exif.getAttribute(ExifInterface.TAG_LENS_MODEL)

            /*
             * Flash
             *
             * Guardamos el valor EXIF original.
             *
             * Ejemplo:
             * 0 = no disparado
             * 1 = disparado
             *
             * El bit 0 indica si el flash fue disparado.
             */
            val flashFired = flashRaw?.let {
                (it and 0x01) != 0
            }

            /*
             * Color / balance de blancos
             */
            val whiteBalance = exif
                .getAttributeInt(
                    ExifInterface.TAG_WHITE_BALANCE,
                    -1
                )
                .takeIf { it >= 0 }

            val colorSpace = exif
                .getAttributeInt(
                    ExifInterface.TAG_COLOR_SPACE,
                    -1
                )
                .takeIf { it >= 0 }

            /*
             * Medición
             */
            val meteringMode = exif
                .getAttributeInt(
                    ExifInterface.TAG_METERING_MODE,
                    -1
                )
                .takeIf { it >= 0 }

            /*
             * Orientación
             */
            val orientation = exif
                .getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )

            /*
             * GPS
             */
            val latitude =
                latLong?.getOrNull(0)
                    ?.takeUnless { it == 0.0 }

            val longitude =
                latLong?.getOrNull(1)
                    ?.takeUnless { it == 0.0 }

            val gpsDate =
                exif.getAttribute(ExifInterface.TAG_GPS_DATESTAMP)

            val gpsTime =
                exif.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP)

            /*
             * Construimos el modelo
             */
            return PhotoMetadata(

                width = width,

                height = height,

                make = make,

                model = model,

                software = software,

                dateTime = dateTime,

                dateTimeOriginal = dateTimeOriginal,

                dateTimeDigitized = dateTimeDigitized,

                iso = iso,

                aperture = aperture,

                exposureTime = exposureTime,

                focalLength = focalLength,

                focalLength35mm = focalLength35mm,

                flash = flashRaw,

                flashFired = flashFired,

                whiteBalance = whiteBalance,

                meteringMode = meteringMode,

                orientation = orientation,

                latitude = latitude,

                longitude = longitude,

                gpsDate = gpsDate,

                gpsTime = gpsTime,

                exposureBias = exposureBias,

                exposureProgram = exposureProgram,

                digitalZoom = digitalZoom,

                lensMake = lensMake,

                lensModel = lensModel,

                colorSpace = colorSpace
            )
        }
    }
}