package com.alice.app.data.models

data class ChatMessage(
    val content: String,
    val isFromUser: Boolean,
    val id: Long = System.nanoTime()
)
