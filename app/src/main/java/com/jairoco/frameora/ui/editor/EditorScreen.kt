package com.jairoco.frameora.ui.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jairoco.frameora.domain.editor.model.MetadataField

@Composable
fun EditorScreen(
    state: EditorUiState,
    onBack: () -> Unit,
    onAddText: () -> Unit,
    onMetadataFieldChange: (MetadataField, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        /*
         * Barra superior
         */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = onBack
            ) {
                Text("←")
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Editor",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * Vista previa
         */
        state.originalUri?.let { uri ->

            EditorPreview(
                uri = uri,
                frame = null,
                texts = state.edit.texts,
                metadata = state.metadata,
                metadataConfig = state.edit.metadata,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        /*
         * Campos de metadata
         */
        Text(
            text = "Campos de información",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        MetadataFieldSwitch(
            label = "Fabricante",
            field = MetadataField.MAKE,
            checked = state.edit.metadata.showMake,
            onCheckedChange = onMetadataFieldChange
        )

        MetadataFieldSwitch(
            label = "Modelo",
            field = MetadataField.MODEL,
            checked = state.edit.metadata.showModel,
            onCheckedChange = onMetadataFieldChange
        )

        MetadataFieldSwitch(
            label = "Distancia focal",
            field = MetadataField.FOCAL_LENGTH,
            checked = state.edit.metadata.showFocalLength,
            onCheckedChange = onMetadataFieldChange
        )

        MetadataFieldSwitch(
            label = "Apertura",
            field = MetadataField.APERTURE,
            checked = state.edit.metadata.showAperture,
            onCheckedChange = onMetadataFieldChange
        )

        MetadataFieldSwitch(
            label = "Velocidad",
            field = MetadataField.EXPOSURE_TIME,
            checked = state.edit.metadata.showExposureTime,
            onCheckedChange = onMetadataFieldChange
        )

        MetadataFieldSwitch(
            label = "ISO",
            field = MetadataField.ISO,
            checked = state.edit.metadata.showIso,
            onCheckedChange = onMetadataFieldChange
        )

        MetadataFieldSwitch(
            label = "Fecha",
            field = MetadataField.DATE,
            checked = state.edit.metadata.showDate,
            onCheckedChange = onMetadataFieldChange
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        /*
         * Campos adicionales
         */
        Text(
            text = "Más información",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        MetadataFieldSwitch(
            label = "Lente",
            field = MetadataField.LENS,
            checked = state.edit.metadata.showLens,
            onCheckedChange = onMetadataFieldChange
        )

        MetadataFieldSwitch(
            label = "Equivalente 35 mm",
            field = MetadataField.FOCAL_LENGTH_35MM,
            checked = state.edit.metadata.showFocalLength35mm,
            onCheckedChange = onMetadataFieldChange
        )

        MetadataFieldSwitch(
            label = "Zoom digital",
            field = MetadataField.DIGITAL_ZOOM,
            checked = state.edit.metadata.showDigitalZoom,
            onCheckedChange = onMetadataFieldChange
        )

        MetadataFieldSwitch(
            label = "GPS",
            field = MetadataField.GPS,
            checked = state.edit.metadata.showGps,
            onCheckedChange = onMetadataFieldChange
        )

        MetadataFieldSwitch(
            label = "Flash",
            field = MetadataField.FLASH,
            checked = state.edit.metadata.showFlash,
            onCheckedChange = onMetadataFieldChange
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        /*
         * Agregar texto
         */
        Button(
            onClick = onAddText,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar texto")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * Exportar
         */
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Exportar")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )
    }
}