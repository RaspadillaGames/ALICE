package com.alice.app.data.sample

import com.alice.app.data.models.ChatMessage
import com.alice.app.data.models.DocumentItem
import com.alice.app.data.models.Flashcard
import com.alice.app.data.models.Task
import com.alice.app.data.models.TaskPriority
import com.alice.app.data.models.UserProfile

object SampleData {
    val userProfile = UserProfile(
        name = "Rodrigo",
        level = 7,
        xp = 2450,
        nextLevelXp = 3000,
        streakDays = 12,
        learningStyle = "Mixto",
        explanationType = "Mediana",
        recommendedSession = "20 minutos",
        achievements = listOf(
            "Primer documento analizado",
            "7 días de estudio",
            "Maestro de flashcards",
            "Planificador disciplinado"
        )
    )

    val documents = listOf(
        DocumentItem(
            name = "Informe de Programación.pdf",
            type = "PDF",
            description = "Documento académico listo para resumir"
        ),
        DocumentItem(
            name = "Resumen de Software.docx",
            type = "DOCX",
            description = "Texto de referencia para estudiar"
        ),
        DocumentItem(
            name = "Apuntes de Kotlin.jpg",
            type = "Imagen",
            description = "Preparado para futura visión artificial"
        )
    )

    val initialFlashcards = listOf(
        Flashcard(
            question = "¿Qué es Jetpack Compose?",
            answer = "Es el kit moderno de Android para crear interfaces nativas con Kotlin de forma declarativa."
        ),
        Flashcard(
            question = "¿Qué es una ViewModel?",
            answer = "Es una clase que conserva el estado de la UI y separa la lógica de presentación de las pantallas."
        ),
        Flashcard(
            question = "¿Qué es una API?",
            answer = "Es una interfaz que permite que una aplicación se comunique con otro servicio o sistema."
        )
    )

    val initialTasks = listOf(
        Task(
            title = "Repasar arquitectura MVVM",
            description = "Preparar ejemplos para explicar ViewModel y Repository.",
            priority = TaskPriority.HIGH
        ),
        Task(
            title = "Crear flashcards de Kotlin",
            description = "Generar tarjetas de repaso para funciones, clases y coroutines.",
            priority = TaskPriority.MEDIUM
        ),
        Task(
            title = "Organizar apuntes",
            description = "Separar documentos por tema antes de estudiar.",
            priority = TaskPriority.LOW
        )
    )

    val initialMessages = listOf(
        ChatMessage(
            content = "Sí, señor. Estoy lista para ayudarle con sus estudios, documentos y proyectos.",
            isFromUser = false
        )
    )
}
