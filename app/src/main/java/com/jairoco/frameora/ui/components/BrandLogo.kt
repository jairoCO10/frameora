package com.jairoco.frameora.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jairoco.frameora.domain.brand.CameraBrand

@Composable
fun BrandLogo(
    brand: CameraBrand?,
    modifier: Modifier = Modifier
) {
    if (brand == null || brand.logoAsset == null) {
        return
    }

    AsyncImage(
        model = "file:///android_asset/brands/${brand.logoAsset}",
        contentDescription = "Logo de ${brand.name}",
        modifier = modifier.size(80.dp)
    )
}