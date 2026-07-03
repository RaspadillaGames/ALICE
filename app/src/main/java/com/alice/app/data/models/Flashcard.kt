package com.alice.app.data.models

data class Flashcard(
    val id: Long = System.nanoTime(),
    val question: String,
    val answer: String
)
