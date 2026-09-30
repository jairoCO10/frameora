package com.jairoco.frameora.ui.editor

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import com.jairoco.frameora.domain.editor.model.MetadataDisplayConfig
import com.jairoco.frameora.domain.editor.model.MetadataStyle
import com.jairoco.frameora.domain.editor.model.TextElement
import com.jairoco.frameora.domain.metadata.MetadataFormatter
import com.jairoco.frameora.domain.model.PhotoMetadata

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.Alignment
import com.jairoco.frameora.ui.components.BrandLogo
import com.jairoco.frameora.domain.brand.BrandResolver

import com.jairoco.frameora.domain.brand.CameraBrand

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width


@Composable
fun EditorPreview(
    uri: Uri,
    frame: FrameConfig?,
    texts: List<TextElement> = emptyList(),
    metadata: PhotoMetadata? = null,
    metadataConfig: MetadataDisplayConfig = MetadataDisplayConfig(),
    style: MetadataStyle = MetadataStyle(),
    modifier: Modifier = Modifier
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

    Column( modifier = modifier.fillMaxWidth() ) {

        /*
         * FOTO + ELEMENTOS
         */
        BoxWithConstraints(modifier = Modifier.fillMaxWidth().aspectRatio(aspectRatio) ) {

            AsyncImage(
                model = uri,
                contentDescription = "Fotografía en edición",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
//            BrandLogo(
//                brand = BrandResolver.resolve(metadata?.make),
//                modifier = Modifier
//            )

            texts.forEach { textElement ->

                val x = textElement.x.coerceIn(0f, 1f)
                val y = textElement.y.coerceIn(0f, 1f)

                Text(
                    text = textElement.text,
                    fontSize = textElement.fontSize.sp,
                    fontWeight = if (textElement.bold) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    },
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(
                            x = maxWidth * (x - 0.5f),
                            y = maxHeight * (y - 0.5f)
                        )
                )
            }
        }

        /*
         * FRANJA DE METADATA
         */
        MetadataBar(
            metadata = metadata,
            config = metadataConfig,
            style = style,
            brand = BrandResolver.resolve(metadata?.make)
        )
    }
}

@Composable
private fun MetadataBar(
    metadata: PhotoMetadata?,
    config: MetadataDisplayConfig,
    style: MetadataStyle,
    brand: CameraBrand?
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(style.backgroundColor)
            .padding(
                horizontal = style.horizontalPadding.dp,
                vertical = style.verticalPadding.dp
            )
    ) {

        /*
         * Sin metadata
         */
        if (metadata == null) {
            Text(
                text = "Sin información EXIF",
                fontSize = style.secondarySize.sp,
                color = style.secondaryTextColor
            )
            return
        }

        /*
         * Fabricante
         */
//        if (config.showMake) {
//            metadata.make
//                ?.takeIf { it.isNotBlank() }
//                ?.let { make ->
//                    Text(
//                        text = make.uppercase(),
//                        fontSize = style.manufacturerSize.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = style.primaryTextColor
//                    )
//                }
//        }

        /*
         * Modelo
         */
//        if (config.showModel) {
//            metadata.model
//                ?.takeIf { it.isNotBlank() }
//                ?.let { model ->
//                    Text(
//                        text = model,
//                        fontSize = style.modelSize.sp,
//                        color = style.secondaryTextColor
//                    )
//                }
//        }

        /*
 * Marca + modelo
 */
        if (config.showMake || config.showModel) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (config.showMake && brand != null) {

                    BrandLogo(
                        brand = brand,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )
                } else if (
                    config.showMake &&
                    metadata.make?.isNotBlank() == true
                ) {

                    Text(
                        text = metadata.make.uppercase(),
                        fontSize = style.manufacturerSize.sp,
                        fontWeight = FontWeight.Bold,
                        color = style.primaryTextColor
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )
                }

                if (config.showModel) {

                    metadata.model
                        ?.takeIf { it.isNotBlank() }
                        ?.let { model ->

                            Text(
                                text = model,
                                fontSize = style.modelSize.sp,
                                color = style.secondaryTextColor
                            )
                        }
                }
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
                fontSize = style.metadataSize.sp,
                color = style.primaryTextColor
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
                    fontSize = style.secondarySize.sp,
                    color = style.secondaryTextColor
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
                        fontSize = style.secondarySize.sp,
                        color = style.secondaryTextColor
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
                    fontSize = style.secondarySize.sp,
                    color = style.secondaryTextColor
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
                    fontSize = style.secondarySize.sp,
                    color = style.secondaryTextColor
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
                    fontSize = style.secondarySize.sp,
                    color = style.secondaryTextColor
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
                        fontSize = style.secondarySize.sp,
                        color = style.dateTextColor
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