package com.alice.app.data.models

data class Task(
    val id: Long = System.nanoTime(),
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val isCompleted: Boolean = false
)

enum class TaskPriority(val label: String) {
    LOW("Baja"),
    MEDIUM("Media"),
    HIGH("Alta")
}
