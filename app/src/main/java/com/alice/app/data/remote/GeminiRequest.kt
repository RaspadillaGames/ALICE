package com.alice.app.data.remote

data class GeminiRequest(
    val contents: List<Content>,
    val systemInstruction: Content,
    val generationConfig: GenerationConfig = GenerationConfig()
)

data class Content(
    val parts: List<Part>,
    val role: String? = null
)

data class Part(
    val text: String
)

data class GenerationConfig(
    val temperature: Double = 0.7,
    val topP: Double = 0.95,
    val maxOutputTokens: Int = 2048
)
