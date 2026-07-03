package com.alice.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alice.app.data.models.AiUiState
import com.alice.app.data.remote.GeminiConstants
import com.alice.app.data.repository.AliceRepository
import kotlinx.coroutines.launch

class StudyPlanViewModel(
    private val repository: AliceRepository = AliceRepository()
) : ViewModel() {
    val durations = listOf("20 minutos", "30 minutos", "45 minutos")

    var topic by mutableStateOf("")
        private set

    var selectedDuration by mutableStateOf(durations[1])
        private set

    var uiState by mutableStateOf<AiUiState>(AiUiState.Idle)
        private set

    fun onTopicChange(value: String) {
        topic = value
    }

    fun onDurationChange(value: String) {
        selectedDuration = value
    }

    fun createPlan() {
        val cleanTopic = topic.trim()
        if (cleanTopic.isBlank() || uiState == AiUiState.Loading) return

        uiState = AiUiState.Loading
        viewModelScope.launch {
            repository.generateStudyPlan(cleanTopic, selectedDuration)
                .onSuccess { uiState = AiUiState.Success(it) }
                .onFailure { error ->
                    uiState = AiUiState.Error(error.message ?: GeminiConstants.EMPTY_RESPONSE)
                }
        }
    }
}
