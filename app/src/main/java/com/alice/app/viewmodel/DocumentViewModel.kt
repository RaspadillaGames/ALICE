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

class DocumentViewModel(
    private val repository: AliceRepository = AliceRepository()
) : ViewModel() {
    var documentText by mutableStateOf("")
        private set

    var uiState by mutableStateOf<AiUiState>(AiUiState.Idle)
        private set

    fun onDocumentTextChange(value: String) {
        documentText = value
    }

    fun summarize() {
        val cleanText = documentText.trim()
        if (cleanText.isBlank() || uiState == AiUiState.Loading) return

        uiState = AiUiState.Loading
        viewModelScope.launch {
            repository.summarizeDocument(cleanText)
                .onSuccess { uiState = AiUiState.Success(it) }
                .onFailure { error ->
                    uiState = AiUiState.Error(error.message ?: GeminiConstants.EMPTY_RESPONSE)
                }
        }
    }
}
