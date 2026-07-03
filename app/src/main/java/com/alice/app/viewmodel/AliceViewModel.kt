package com.alice.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alice.app.data.models.AiUiState
import com.alice.app.data.models.ChatMessage
import com.alice.app.data.remote.GeminiConstants
import com.alice.app.data.repository.AliceRepository
import com.alice.app.data.sample.SampleData
import kotlinx.coroutines.launch

class AliceViewModel(
    private val repository: AliceRepository = AliceRepository()
) : ViewModel() {
    var messages by mutableStateOf(SampleData.initialMessages)
        private set

    var uiState by mutableStateOf<AiUiState>(AiUiState.Idle)
        private set

    fun sendMessage(message: String) {
        val cleanMessage = message.trim()
        if (cleanMessage.isBlank() || uiState == AiUiState.Loading) return

        messages = messages + ChatMessage(content = cleanMessage, isFromUser = true)
        uiState = AiUiState.Loading

        viewModelScope.launch {
            repository.sendChatMessage(cleanMessage)
                .onSuccess { response ->
                    messages = messages + ChatMessage(content = response, isFromUser = false)
                    uiState = AiUiState.Success(response)
                }
                .onFailure { error ->
                    val messageError = error.message ?: GeminiConstants.CHAT_ERROR
                    messages = messages + ChatMessage(content = messageError, isFromUser = false)
                    uiState = AiUiState.Error(messageError)
                }
        }
    }
}
