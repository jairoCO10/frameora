package com.jairoco.frameora.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jairoco.frameora.domain.model.PhotoMetadata

@Composable
fun PhotoPreview(
    uri: Uri,
    metadata: PhotoMetadata?
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

    AsyncImage(
        model = uri,
        contentDescription = "Fotografía seleccionada",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(Color.Black),
        contentScale = ContentScale.Fit
    )
}