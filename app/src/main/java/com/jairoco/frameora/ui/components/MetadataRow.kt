package com.jairoco.frameora.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MetadataRow(
    label: String,
    value: String?
) {
    Text(
        text = "$label: ${
            if (value.isNullOrBlank()) {
                "No disponible"
            } else {
                value
            }
        }",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    )
}