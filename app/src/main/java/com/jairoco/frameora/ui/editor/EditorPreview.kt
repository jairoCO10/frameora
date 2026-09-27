package com.jairoco.frameora.ui.editor

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.jairoco.frameora.domain.editor.model.FrameConfig
import com.jairoco.frameora.domain.editor.model.TextElement
import com.jairoco.frameora.domain.metadata.MetadataFormatter
import com.jairoco.frameora.domain.model.PhotoMetadata
import com.jairoco.frameora.domain.editor.model.MetadataDisplayConfig

@Composable
fun EditorPreview(
    uri: Uri,
    frame: FrameConfig?,
    texts: List<TextElement> = emptyList(),
    metadata: PhotoMetadata? = null,
    modifier: Modifier = Modifier,
    metadataConfig: MetadataDisplayConfig = MetadataDisplayConfig(),
) {

    val aspectRatio =
        if (
            metadata?.width != null &&
            metadata.height != null &&
            metadata.width > 0 &&
            metadata.height > 0
        ) {
            val width = metadata.width.toFloat()
            val height = metadata.height.toFloat()

            val orientation = metadata.orientation

            val rotated =
                orientation == 5 ||
                        orientation == 6 ||
                        orientation == 7 ||
                        orientation == 8

            if (rotated) {
                height / width
            } else {
                width / height
            }

        } else {
            1f
        }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        /*
         * FOTO
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
        ) {

            AsyncImage(
                model = uri,
                contentDescription = "Fotografía en edición",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        /*
         * FRANJA DE METADATA
         */
        MetadataBar(
            metadata = metadata,
            config = metadataConfig
        )
    }
}


@Composable
private fun MetadataBar(
    metadata: PhotoMetadata?,
    config: MetadataDisplayConfig
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            )
    ) {

        if (metadata == null) {
            Text(
                text = "Sin información EXIF",
                fontSize = 14.sp,
                color = Color.Gray
            )
            return
        }

        /*
         * Fabricante
         */
        if (config.showMake) {
            metadata.make
                ?.takeIf { it.isNotBlank() }
                ?.let { make ->

                    Text(
                        text = make.uppercase(),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
        }

        /*
         * Modelo
         */
        if (config.showModel) {
            metadata.model
                ?.takeIf { it.isNotBlank() }
                ?.let { model ->

                    Text(
                        text = model,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
        }

        /*
         * Parámetros fotográficos
         */
        val photographicData = buildList {

            if (config.showFocalLength) {
                add(
                    MetadataFormatter.formatFocalLength(
                        metadata.focalLength
                    )
                )
            }

            if (config.showAperture) {
                add(
                    MetadataFormatter.formatAperture(
                        metadata.aperture
                    )
                )
            }

            if (config.showExposureTime) {
                add(
                    MetadataFormatter.formatExposureTime(
                        metadata.exposureTime
                    )
                )
            }

            if (config.showIso) {
                add(
                    MetadataFormatter.formatIso(
                        metadata.iso
                    )
                )
            }

        }.filter {
            it != "--" && it != "ISO --"
        }

        if (photographicData.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = photographicData.joinToString("  ·  "),
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        /*
         * Lente
         */
        if (config.showLens) {

            val lens =
                listOfNotNull(
                    metadata.lensMake
                        ?.takeIf { it.isNotBlank() },

                    metadata.lensModel
                        ?.takeIf { it.isNotBlank() }
                ).joinToString(" ")

            if (lens.isNotBlank()) {

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = lens,
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }

        /*
         * Equivalente 35 mm
         */
        if (config.showFocalLength35mm) {

            metadata.focalLength35mm
                ?.let { focal35 ->

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Equiv. 35 mm: ${focal35} mm",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
        }

        /*
         * Zoom digital
         */
        if (config.showDigitalZoom) {

            val zoom =
                MetadataFormatter.formatDigitalZoom(
                    metadata.digitalZoom
                )

            if (zoom != "--") {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Zoom digital: $zoom",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }

        /*
         * Flash
         */
        if (config.showFlash) {

            val flash =
                MetadataFormatter.formatFlash(
                    metadata.flash
                )

            if (flash != "--") {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Flash: $flash",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }

        /*
         * GPS
         */
        if (config.showGps) {

            val gps =
                MetadataFormatter.formatGps(
                    metadata.latitude,
                    metadata.longitude
                )

            if (gps != "--") {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "GPS: $gps",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }

        /*
         * Fecha
         */
        if (config.showDate) {

            metadata.dateTimeOriginal
                ?.takeIf { it.isNotBlank() }
                ?.let { date ->

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = formatDisplayDate(date),
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
        }
    }
}

private fun formatDisplayDate(
    value: String
): String {

    val formatted = MetadataFormatter.formatDateTime(value)

    if (formatted == "--") {
        return formatted
    }

    return try {

        val parts = formatted.split(" ")

        if (parts.size < 2) {
            return formatted
        }

        val dateParts = parts[0].split("/")

        if (dateParts.size != 3) {
            return formatted
        }

        val day = dateParts[0]
        val month = dateParts[1]
        val year = dateParts[2]
        val time = parts[1]

        val monthName = when (month) {
            "01" -> "ENE"
            "02" -> "FEB"
            "03" -> "MAR"
            "04" -> "ABR"
            "05" -> "MAY"
            "06" -> "JUN"
            "07" -> "JUL"
            "08" -> "AGO"
            "09" -> "SEP"
            "10" -> "OCT"
            "11" -> "NOV"
            "12" -> "DIC"
            else -> month
        }

        "$day $monthName $year  ·  $time"

    } catch (exception: Exception) {
        formatted
    }
}