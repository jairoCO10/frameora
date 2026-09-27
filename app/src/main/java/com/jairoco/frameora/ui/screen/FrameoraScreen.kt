package com.jairoco.frameora.ui.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.jairoco.frameora.domain.metadata.MetadataFormatter
import com.jairoco.frameora.ui.components.MetadataRow
import com.jairoco.frameora.ui.components.MetadataSection
import com.jairoco.frameora.ui.components.PhotoPreview
import com.jairoco.frameora.ui.editor.EditorScreen
import com.jairoco.frameora.ui.editor.EditorViewModel

import androidx.compose.runtime.LaunchedEffect


@Composable
fun FrameoraScreen(
    modifier: Modifier = Modifier,
    viewModel: FrameoraViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val editorViewModel: EditorViewModel = viewModel()
    val editorUiState by editorViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(
        uiState.selectedImageUri,
        uiState.photoMetadata
    ) {
        val uri = uiState.selectedImageUri
        val metadata = uiState.photoMetadata

        if (uri != null) {
            editorViewModel.setPhoto(
                uri = uri,
                metadata = metadata
            )
        }
    }

    var showEditor by remember {
        mutableStateOf(false)
    }

    /*
     * Selector de fotografías
     */
    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->

            if (uri != null) {

                // Procesamos la fotografía y sus metadatos
                viewModel.selectPhoto(uri)

                // Entregamos la misma fotografía al editor
                editorViewModel.setPhoto(uri)

                // Entramos directamente al editor
                showEditor = true
            }
        }

    /*
     * Si estamos en el editor,
     * mostramos EditorScreen.
     */
    if (showEditor) {

        EditorScreen(
            state = editorUiState,

            onBack = {
                showEditor = false
            },

            onAddText = {
                editorViewModel.addText("FRAMEORA")
            },

            onMetadataFieldChange = { field, enabled ->
                editorViewModel.setMetadataField(
                    field = field,
                    enabled = enabled
                )
            }
        )

        return
    }

    /*
     * Pantalla principal de Frameora
     */
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Frameora",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Tu foto. Tu historia.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        /*
         * Vista previa de la fotografía
         */
        uiState.selectedImageUri?.let { uri ->

            PhotoPreview(
                uri = uri,
                metadata = uiState.photoMetadata
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        /*
         * Botones de fotografía
         */
        if (uiState.selectedImageUri == null) {

            Button(
                onClick = {

                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "📷 Seleccionar fotografía"
                )
            }

        } else {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                /*
                 * Cambiar fotografía
                 */
                OutlinedButton(
                    onClick = {

                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "📷 Cambiar"
                    )
                }

                /*
                 * Volver al editor
                 */
                Button(
                    onClick = {
                        showEditor = true
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "✏️ Editar"
                    )
                }
            }
        }

        /*
         * Indicador de carga
         */
        if (uiState.isLoading) {

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            CircularProgressIndicator()
        }

        /*
         * Error
         */
        uiState.errorMessage?.let { error ->

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        /*
         * Metadatos
         */
        uiState.photoMetadata?.let { metadata ->

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "Información de la fotografía",
                style = MaterialTheme.typography.titleLarge
            )

            MetadataSection(
                title = "📷 Cámara"
            ) {
                MetadataRow(
                    label = "Fabricante",
                    value = metadata.make
                )

                MetadataRow(
                    label = "Modelo",
                    value = metadata.model
                )

                MetadataRow(
                    label = "Software",
                    value = metadata.software
                )
            }

            MetadataSection(
                title = "📐 Imagen"
            ) {
                MetadataRow(
                    label = "Resolución",
                    value =
                        if (
                            metadata.width != null &&
                            metadata.height != null
                        ) {
                            "${metadata.width} × ${metadata.height}"
                        } else {
                            null
                        }
                )

                MetadataRow(
                    label = "Orientación",
                    value = metadata.orientation?.toString()
                )
            }

            MetadataSection(
                title = "⚙️ Exposición"
            ) {
                MetadataRow(
                    label = "ISO",
                    value = MetadataFormatter.formatIso(
                        metadata.iso
                    )
                )

                MetadataRow(
                    label = "Apertura",
                    value = MetadataFormatter.formatAperture(
                        metadata.aperture
                    )
                )

                MetadataRow(
                    label = "Velocidad",
                    value = MetadataFormatter.formatExposureTime(
                        metadata.exposureTime
                    )
                )

                MetadataRow(
                    label = "Compensación",
                    value = MetadataFormatter.formatExposureBias(
                        metadata.exposureBias
                    )
                )
            }

            MetadataSection(
                title = "🔭 Óptica"
            ) {
                MetadataRow(
                    label = "Focal",
                    value = MetadataFormatter.formatFocalLength(
                        metadata.focalLength
                    )
                )

                MetadataRow(
                    label = "Equivalente 35 mm",
                    value = metadata.focalLength35mm?.let {
                        "$it mm"
                    }
                )

                MetadataRow(
                    label = "Zoom digital",
                    value = MetadataFormatter.formatDigitalZoom(
                        metadata.digitalZoom
                    )
                )

                MetadataRow(
                    label = "Fabricante lente",
                    value = metadata.lensMake
                )

                MetadataRow(
                    label = "Modelo lente",
                    value = metadata.lensModel
                )
            }

            MetadataSection(
                title = "⚡ Flash"
            ) {
                MetadataRow(
                    label = "Estado",
                    value = MetadataFormatter.formatFlash(
                        metadata.flash
                    )
                )
            }

            MetadataSection(
                title = "🌈 Color"
            ) {
                MetadataRow(
                    label = "Balance de blancos",
                    value = MetadataFormatter.formatWhiteBalance(
                        metadata.whiteBalance
                    )
                )

                MetadataRow(
                    label = "Espacio de color",
                    value = metadata.colorSpace?.toString()
                )
            }

            MetadataSection(
                title = "📅 Fechas"
            ) {
                MetadataRow(
                    label = "Fecha",
                    value = metadata.dateTime
                )

                MetadataRow(
                    label = "Original",
                    value = metadata.dateTimeOriginal
                )

                MetadataRow(
                    label = "Digitalización",
                    value = metadata.dateTimeDigitized
                )
            }

            MetadataSection(
                title = "📍 Ubicación"
            ) {
                MetadataRow(
                    label = "Coordenadas",
                    value = MetadataFormatter.formatGps(
                        metadata.latitude,
                        metadata.longitude
                    )
                )

                MetadataRow(
                    label = "Fecha GPS",
                    value = metadata.gpsDate
                )

                MetadataRow(
                    label = "Hora GPS",
                    value = metadata.gpsTime
                )
            }
        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )
    }
}