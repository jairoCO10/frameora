package com.jairoco.frameora.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MetadataSection(
    title: String,
    content: @Composable () -> Unit
) {
    Spacer(
        modifier = Modifier.height(12.dp)
    )

    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(
        modifier = Modifier.height(8.dp)
    )

    Column {
        content()
    }
}