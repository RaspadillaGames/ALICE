package com.alice.app.data.repository

import com.alice.app.BuildConfig
import com.alice.app.data.models.Task
import com.alice.app.data.remote.Content
import com.alice.app.data.remote.GeminiApiService
import com.alice.app.data.remote.GeminiClient
import com.alice.app.data.remote.GeminiConstants
import com.alice.app.data.remote.GeminiRequest
import com.alice.app.data.remote.Part
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AliceRepository(
    private val apiService: GeminiApiService = GeminiClient.apiService,
    private val apiKey: String = BuildConfig.GEMINI_API_KEY
) {
    suspend fun sendChatMessage(message: String): Result<String> {
        return askGemini(message)
    }

    suspend fun generateFlashcards(topic: String): Result<String> {
        val prompt =
            "Genera 5 flashcards educativas sobre el tema: $topic. " +
                "Devuelve el resultado en español, con formato claro. " +
                "Cada flashcard debe tener Pregunta y Respuesta. " +
                "Mantén explicaciones breves y útiles."
        return askGemini(prompt)
    }

    suspend fun generateStudyPlan(topic: String, duration: String): Result<String> {
        val prompt =
            "Crea un plan de estudio paso a paso para aprender $topic. " +
                "Divídelo en sesiones de $duration. Incluye objetivos, actividades y recomendaciones. " +
                "Responde en español con estructura ordenada."
        return askGemini(prompt)
    }

    suspend fun summarizeDocument(text: String): Result<String> {
        val prompt =
            "Resume el siguiente documento en español. Organiza la respuesta en: " +
                "ideas principales, resumen breve, conclusiones y recomendaciones: $text"
        return askGemini(prompt)
    }

    suspend fun organizeTasks(tasks: List<Task>): Result<String> {
        val taskText = tasks.joinToString(separator = "\n") { task ->
            "- ${task.title} (${task.priority.label}): ${task.description}"
        }
        val prompt =
            "Analiza estas tareas y dame una recomendación breve para organizarlas por prioridad. " +
                "Tareas: $taskText. Responde en español, con tono motivador y claro."
        return askGemini(prompt)
    }

    // Repositorio central de funciones de ALICE.
    private suspend fun askGemini(prompt: String): Result<String> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank() || apiKey == "PEGAR_AQUI_MI_API_KEY") {
            return@withContext Result.failure(IllegalStateException(GeminiConstants.API_KEY_MISSING))
        }

        runCatching {
            val request = GeminiRequest(
                systemInstruction = Content(parts = listOf(Part(GeminiConstants.SYSTEM_PROMPT))),
                contents = listOf(
                    Content(
                        role = "user",
                        parts = listOf(Part(prompt))
                    )
                )
            )

            val response = apiService.generateContent(
                model = GeminiConstants.MODEL_NAME,
                apiKey = apiKey,
                request = request
            )

            response.candidates
                ?.firstOrNull()
                ?.content
                ?.parts
                ?.joinToString(separator = "\n") { it.text }
                ?.trim()
                ?.takeIf { it.isNotBlank() }
                ?: throw IllegalStateException(GeminiConstants.EMPTY_RESPONSE)
        }.recoverCatching { error ->
            when (error) {
                is IOException -> throw IOException(GeminiConstants.CONNECTION_ERROR, error)
                is HttpException -> throw IllegalStateException(GeminiConstants.CHAT_ERROR, error)
                else -> throw error
            }
        }
    }
}
