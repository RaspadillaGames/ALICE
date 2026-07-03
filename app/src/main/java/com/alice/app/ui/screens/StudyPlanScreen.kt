package com.alice.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alice.app.data.models.AiUiState
import com.alice.app.ui.components.AiResultCard
import com.alice.app.ui.components.AlicePrimaryButton
import com.alice.app.ui.components.ErrorCard
import com.alice.app.ui.components.LoadingBubble
import com.alice.app.ui.components.SectionTitle
import com.alice.app.ui.theme.AlicePrimary
import com.alice.app.ui.theme.AliceSurfaceVariant
import com.alice.app.ui.theme.AliceTextSecondary
import com.alice.app.viewmodel.StudyPlanViewModel

@Composable
fun StudyPlanScreen(
    viewModel: StudyPlanViewModel
) {
    val loading = viewModel.uiState == AiUiState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Plan de estudio personalizado",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "ALICE puede convertir un tema en sesiones concretas y fáciles de seguir.",
            style = MaterialTheme.typography.bodyMedium,
            color = AliceTextSecondary
        )
        OutlinedTextField(
            value = viewModel.topic,
            onValueChange = viewModel::onTopicChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading,
            label = { Text("Tema") },
            placeholder = { Text("Ejemplo: Arquitectura limpia en Android") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AlicePrimary,
                unfocusedBorderColor = AliceSurfaceVariant
            )
        )
        SectionTitle(text = "Duración por sesión")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            viewModel.durations.forEach { duration ->
                FilterChip(
                    selected = duration == viewModel.selectedDuration,
                    onClick = { viewModel.onDurationChange(duration) },
                    label = { Text(duration) },
                    enabled = !loading
                )
            }
        }
        AlicePrimaryButton(
            text = "Crear plan con ALICE",
            onClick = viewModel::createPlan,
            enabled = viewModel.topic.isNotBlank(),
            loading = loading
        )
        if (loading) {
            LoadingBubble(text = "ALICE está diseñando su plan…")
        }
        when (val state = viewModel.uiState) {
            is AiUiState.Error -> ErrorCard(message = state.message)
            is AiUiState.Success -> AiResultCard(text = state.text)
            else -> Unit
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}
