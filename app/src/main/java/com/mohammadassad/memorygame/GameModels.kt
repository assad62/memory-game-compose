package com.mohammadassad.memorygame

import androidx.compose.ui.graphics.vector.ImageVector

// Data model for each card
data class CardData(
    val id: Int,
    val iconType: ImageVector,
    var isMatched: Boolean = false
)

// Game status enum
enum class GameStatus {
    PLAYING, WON, LOST
}
