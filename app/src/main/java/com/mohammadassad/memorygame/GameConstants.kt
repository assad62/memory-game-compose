package com.mohammadassad.memorygame

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

object GameConstants {
    // Game configuration
    const val GAME_TIME_SECONDS = 120
    const val MATCH_SCORE = 10
    const val MATCH_DELAY_MS = 800L
    const val TIMER_INTERVAL_MS = 1000L
    const val FLIP_ANIMATION_DURATION_MS = 400
    const val GRID_COLUMNS = 3
    
    // UI constants
    const val CARD_ICON_SIZE_DP = 48
    const val ICON_SIZE_DP = 20
    const val SPACING_DP = 4
    const val GRID_SPACING_DP = 8
    const val CARD_PADDING_DP = 4
    
    // Time warning threshold
    const val TIME_WARNING_THRESHOLD = 30
    
    // Available game icons
    val GAME_ICONS = listOf(
        Icons.Filled.Star,
        Icons.Filled.Favorite,
        Icons.Filled.Home,
        Icons.Filled.Face,
        Icons.Filled.Person,
        Icons.Filled.Email
    )
}
