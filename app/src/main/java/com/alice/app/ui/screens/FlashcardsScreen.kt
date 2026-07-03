package com.alice.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alice.app.data.models.AiUiState
import com.alice.app.ui.components.AlicePrimaryButton
import com.alice.app.ui.components.ErrorCard
import com.alice.app.ui.components.FlashcardItem
import com.alice.app.ui.components.LoadingBubble
import com.alice.app.ui.components.SectionTitle
import com.alice.app.ui.theme.AlicePrimary
import com.alice.app.ui.theme.AliceSurfaceVariant
import com.alice.app.ui.theme.AliceTextSecondary
import com.alice.app.viewmodel.FlashcardViewModel

@Composable
fun FlashcardsScreen(
    viewModel: FlashcardViewModel
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
                    text = "Flashcards de repaso",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Escriba un tema y ALICE creará tarjetas breves para estudiar.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AliceTextSecondary
                )
            }
        }
        item {
            OutlinedTextField(
                value = viewModel.topic,
                onValueChange = viewModel::onTopicChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
                label = { Text("Tema") },
                placeholder = { Text("Ejemplo: Kotlin Coroutines") },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlicePrimary,
                    unfocusedBorderColor = AliceSurfaceVariant
                )
            )
        }
        item {
            AlicePrimaryButton(
                text = "Generar flashcards con IA",
                onClick = viewModel::generateFlashcards,
                enabled = viewModel.topic.isNotBlank(),
                loading = loading
            )
        }
        if (loading) {
            item {
                LoadingBubble(text = "ALICE está creando flashcards…")
            }
        }
        val error = viewModel.uiState as? AiUiState.Error
        if (error != null) {
            item {
                ErrorCard(message = error.message)
            }
        }
        item {
            SectionTitle(text = "Tarjetas")
        }
        items(viewModel.flashcards, key = { it.id }) { flashcard ->
            FlashcardItem(flashcard = flashcard)
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
