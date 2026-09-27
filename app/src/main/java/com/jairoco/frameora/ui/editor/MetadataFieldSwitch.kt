package com.jairoco.frameora.ui.editor

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jairoco.frameora.domain.editor.model.MetadataField

@Composable
fun MetadataFieldSwitch(
    label: String,
    field: MetadataField,
    checked: Boolean,
    onCheckedChange: (MetadataField, Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = checked,
            onCheckedChange = { enabled ->
                onCheckedChange(field, enabled)
            }
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Text(
            text = label
        )
    }
}