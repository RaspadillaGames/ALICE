package com.alice.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alice.app.data.models.AiUiState
import com.alice.app.data.models.TaskPriority
import com.alice.app.ui.components.AiResultCard
import com.alice.app.ui.components.AlicePrimaryButton
import com.alice.app.ui.components.ErrorCard
import com.alice.app.ui.components.LoadingBubble
import com.alice.app.ui.components.TaskCard
import com.alice.app.ui.theme.AlicePrimary
import com.alice.app.ui.theme.AliceSurfaceVariant
import com.alice.app.ui.theme.AliceTextSecondary
import com.alice.app.viewmodel.TaskViewModel

@Composable
fun TasksScreen(
    viewModel: TaskViewModel
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val loading = viewModel.adviceState == AiUiState.Loading

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Gestor de tareas y hábitos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Organice pendientes, marque avances y pida una recomendación a ALICE.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AliceTextSecondary
                    )
                }
            }
            items(viewModel.tasks, key = { it.id }) { task ->
                TaskCard(
                    task = task,
                    onToggle = { viewModel.toggleTask(task.id) },
                    onDelete = { viewModel.deleteTask(task.id) }
                )
            }
            item {
                AlicePrimaryButton(
                    text = "Pedir consejo a ALICE",
                    onClick = viewModel::requestAdvice,
                    enabled = viewModel.tasks.isNotEmpty(),
                    loading = loading
                )
            }
            if (loading) {
                item {
                    LoadingBubble(text = "ALICE está priorizando sus tareas…")
                }
            }
            when (val state = viewModel.adviceState) {
                is AiUiState.Error -> item { ErrorCard(message = state.message) }
                is AiUiState.Success -> item { AiResultCard(text = state.text, title = "Consejo de organización") }
                else -> Unit
            }
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(22.dp),
            containerColor = AlicePrimary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar tarea"
            )
        }
    }

    if (showDialog) {
        AddTaskDialog(
            onDismiss = { showDialog = false },
            onAdd = { title, description, priority ->
                viewModel.addTask(title, description, priority)
                showDialog = false
            }
        )
    }
}

@Composable
private fun AddTaskDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, TaskPriority) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var priority by rememberSaveable { mutableStateOf(TaskPriority.MEDIUM) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva tarea") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Título") },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = taskFieldColors()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Descripción") },
                    shape = RoundedCornerShape(8.dp),
                    minLines = 2,
                    colors = taskFieldColors()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TaskPriority.entries.forEach { item ->
                        FilterChip(
                            selected = item == priority,
                            onClick = { priority = item },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onAdd(title, description, priority) },
                enabled = title.isNotBlank()
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun taskFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = AlicePrimary,
    unfocusedBorderColor = AliceSurfaceVariant
)
