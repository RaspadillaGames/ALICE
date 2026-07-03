package com.alice.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alice.app.data.models.AiUiState
import com.alice.app.data.models.Flashcard
import com.alice.app.data.remote.GeminiConstants
import com.alice.app.data.repository.AliceRepository
import com.alice.app.data.sample.SampleData
import kotlinx.coroutines.launch

class FlashcardViewModel(
    private val repository: AliceRepository = AliceRepository()
) : ViewModel() {
    var topic by mutableStateOf("")
        private set

    var flashcards by mutableStateOf(SampleData.initialFlashcards)
        private set

    var uiState by mutableStateOf<AiUiState>(AiUiState.Idle)
        private set

    fun onTopicChange(value: String) {
        topic = value
    }

    fun generateFlashcards() {
        val cleanTopic = topic.trim()
        if (cleanTopic.isBlank() || uiState == AiUiState.Loading) return

        uiState = AiUiState.Loading
        viewModelScope.launch {
            repository.generateFlashcards(cleanTopic)
                .onSuccess { text ->
                    flashcards = parseFlashcards(text, cleanTopic)
                    uiState = AiUiState.Success(text)
                }
                .onFailure { error ->
                    uiState = AiUiState.Error(error.message ?: GeminiConstants.EMPTY_RESPONSE)
                }
        }
    }

    private fun parseFlashcards(text: String, topic: String): List<Flashcard> {
        val cards = mutableListOf<Flashcard>()
        var currentQuestion: String? = null

        text.lines()
            .map { it.trim().trimStart('-', '*', '•', ' ') }
            .filter { it.isNotBlank() }
            .forEach { line ->
                val normalized = line.replace(Regex("^\\d+[.)]\\s*"), "")
                when {
                    normalized.startsWith("Pregunta", ignoreCase = true) -> {
                        currentQuestion = normalized.substringAfter(":", normalized).trim()
                    }
                    normalized.startsWith("Respuesta", ignoreCase = true) -> {
                        val answer = normalized.substringAfter(":", normalized).trim()
                        val question = currentQuestion
                        if (!question.isNullOrBlank() && answer.isNotBlank()) {
                            cards += Flashcard(question = question, answer = answer)
                            currentQuestion = null
                        }
                    }
                }
            }

        return cards.takeIf { it.isNotEmpty() }
            ?: listOf(
                Flashcard(
                    question = "¿Qué debo recordar sobre $topic?",
                    answer = text
                )
            )
    }
}
