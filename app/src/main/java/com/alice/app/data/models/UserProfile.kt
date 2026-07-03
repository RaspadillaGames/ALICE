package com.alice.app.data.models

data class UserProfile(
    val name: String,
    val level: Int,
    val xp: Int,
    val nextLevelXp: Int,
    val streakDays: Int,
    val learningStyle: String,
    val explanationType: String,
    val recommendedSession: String,
    val achievements: List<String>
)
