package com.alice.app.data.models

sealed class AiUiState {
    data object Idle : AiUiState()
    data object Loading : AiUiState()
    data class Success(val text: String) : AiUiState()
    data class Error(val message: String) : AiUiState()
}
