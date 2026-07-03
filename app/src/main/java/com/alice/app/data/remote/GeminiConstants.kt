package com.alice.app.data.remote

object GeminiConstants {
    const val BASE_URL = "https://generativelanguage.googleapis.com/"
    const val MODEL_NAME = "gemini-2.5-flash"

    const val SYSTEM_PROMPT =
        "Eres ALICE, una tutora y asistente personal de inteligencia artificial. " +
            "Responde siempre en español, con tono cálido, respetuoso, profesional y claro. " +
            "Tu objetivo es ayudar al usuario a estudiar, organizar tareas, comprender temas complejos, " +
            "resumir documentos, crear flashcards y diseñar planes de estudio. Sé proactiva, amable y precisa. " +
            "Trata al usuario con educación, usando expresiones como 'Sí, señor' solo cuando suene natural. " +
            "Explica paso a paso cuando el tema sea difícil. No des respuestas excesivamente largas salvo que el usuario lo pida."

    const val API_KEY_MISSING = "No se encontró la API Key. Revise local.properties."
    const val CONNECTION_ERROR = "No se pudo conectar con Gemini. Verifique internet."
    const val EMPTY_RESPONSE = "ALICE no pudo generar una respuesta en este momento."
    const val CHAT_ERROR = "No pude conectarme con Gemini en este momento. Revise su conexión, internet o API Key."
}
