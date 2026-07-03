package com.alice.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alice.app.data.models.AiUiState
import com.alice.app.data.models.Task
import com.alice.app.data.models.TaskPriority
import com.alice.app.data.remote.GeminiConstants
import com.alice.app.data.repository.AliceRepository
import com.alice.app.data.sample.SampleData
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: AliceRepository = AliceRepository()
) : ViewModel() {
    var tasks by mutableStateOf(SampleData.initialTasks)
        private set

    var adviceState by mutableStateOf<AiUiState>(AiUiState.Idle)
        private set

    fun addTask(title: String, description: String, priority: TaskPriority) {
        val cleanTitle = title.trim()
        if (cleanTitle.isBlank()) return

        tasks = tasks + Task(
            title = cleanTitle,
            description = description.trim().ifBlank { "Sin descripción" },
            priority = priority
        )
    }

    fun toggleTask(taskId: Long) {
        tasks = tasks.map { task ->
            if (task.id == taskId) task.copy(isCompleted = !task.isCompleted) else task
        }
    }

    fun deleteTask(taskId: Long) {
        tasks = tasks.filterNot { it.id == taskId }
    }

    fun requestAdvice() {
        if (tasks.isEmpty() || adviceState == AiUiState.Loading) return

        adviceState = AiUiState.Loading
        viewModelScope.launch {
            repository.organizeTasks(tasks)
                .onSuccess { adviceState = AiUiState.Success(it) }
                .onFailure { error ->
                    adviceState = AiUiState.Error(error.message ?: GeminiConstants.EMPTY_RESPONSE)
                }
        }
    }
}
