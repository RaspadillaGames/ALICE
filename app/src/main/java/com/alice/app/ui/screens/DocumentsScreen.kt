package com.alice.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alice.app.data.models.AiUiState
import com.alice.app.data.models.DocumentItem
import com.alice.app.data.sample.SampleData
import com.alice.app.ui.components.AiResultCard
import com.alice.app.ui.components.AlicePrimaryButton
import com.alice.app.ui.components.ErrorCard
import com.alice.app.ui.components.LoadingBubble
import com.alice.app.ui.components.SectionTitle
import com.alice.app.ui.theme.AliceInfo
import com.alice.app.ui.theme.AlicePrimary
import com.alice.app.ui.theme.AliceSurface
import com.alice.app.ui.theme.AliceSurfaceVariant
import com.alice.app.ui.theme.AliceTextSecondary
import com.alice.app.viewmodel.DocumentViewModel

@Composable
fun DocumentsScreen(
    viewModel: DocumentViewModel
) {
    val loading = viewModel.uiState == AiUiState.Loading

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Resumen de documentos",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Pegue el contenido de un documento y ALICE lo organizará para estudiar.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AliceTextSecondary
                )
            }
        }
        item {
            SectionTitle(text = "Documentos simulados")
        }
        items(SampleData.documents) { document ->
            DocumentCard(document = document)
        }
        item {
            OutlinedTextField(
                value = viewModel.documentText,
                onValueChange = viewModel::onDocumentTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                enabled = !loading,
                label = { Text("Texto del documento") },
                placeholder = { Text("Pegue aquí el texto que desea resumir") },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlicePrimary,
                    unfocusedBorderColor = AliceSurfaceVariant
                )
            )
        }
        item {
            AlicePrimaryButton(
                text = "Resumir con ALICE",
                onClick = viewModel::summarize,
                enabled = viewModel.documentText.isNotBlank(),
                loading = loading
            )
        }
        if (loading) {
            item { LoadingBubble(text = "ALICE está resumiendo el texto…") }
        }
        when (val state = viewModel.uiState) {
            is AiUiState.Error -> item { ErrorCard(message = state.message) }
            is AiUiState.Success -> item { AiResultCard(text = state.text) }
            else -> Unit
        }
        item {
            Text(
                text = "El análisis real de archivos PDF, DOCX e imágenes puede integrarse después con lectura de archivos y Gemini Vision.",
                style = MaterialTheme.typography.bodySmall,
                color = AliceTextSecondary
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun DocumentCard(document: DocumentItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = AliceSurface)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = null,
                tint = AliceInfo
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = document.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${document.type} · ${document.description}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AliceTextSecondary
                )
            }
        }
    }
}
