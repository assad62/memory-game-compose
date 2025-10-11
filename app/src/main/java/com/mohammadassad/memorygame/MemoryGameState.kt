package com.mohammadassad.memorygame

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

class MemoryGameState {
    var cards by mutableStateOf<List<CardData>>(emptyList())
        private set

    var selectedCards by mutableStateOf<List<CardData>>(emptyList())
        private set

    var score by mutableStateOf(0)
        private set

    var timeLeft by mutableStateOf(GameConstants.GAME_TIME_SECONDS)
        private set

    var gameStatus by mutableStateOf(GameStatus.PLAYING)
        private set

    var isCheckingMatch by mutableStateOf(false)
        private set

    // Initialize game with pairs of icons
    fun initializeGame() {
        cards = GameConstants.GAME_ICONS.flatMapIndexed { index, icon ->
            listOf(
                CardData(id = index * 2, iconType = icon),
                CardData(id = index * 2 + 1, iconType = icon)
            )
        }.shuffled()

        resetGameState()
    }

    private fun resetGameState() {
        score = 0
        timeLeft = GameConstants.GAME_TIME_SECONDS
        gameStatus = GameStatus.PLAYING
        selectedCards = emptyList()
        isCheckingMatch = false
    }

    // Handle card click
    fun onCardClick(card: CardData) {
        if (!canClickCard(card)) return
        selectedCards = selectedCards + card
    }

    private fun canClickCard(card: CardData): Boolean {
        return gameStatus == GameStatus.PLAYING && 
               !isCheckingMatch && 
               !selectedCards.any { it.id == card.id } && 
               selectedCards.size < 2
    }

    // Check if two selected cards match
    suspend fun checkMatch() {
        if (selectedCards.size != 2) return

        isCheckingMatch = true
        val (first, second) = selectedCards

        // Wait a bit to show both cards
        delay(GameConstants.MATCH_DELAY_MS)

        if (isMatch(first, second)) {
            handleMatch(first, second)
        }

        clearSelection()
    }

    private fun isMatch(first: CardData, second: CardData): Boolean {
        return first.iconType == second.iconType
    }

    private fun handleMatch(first: CardData, second: CardData) {
        // Remove matched cards from the list
        cards = cards.filter { it.id != first.id && it.id != second.id }
        score += GameConstants.MATCH_SCORE

        // Check if all cards are matched (list is empty)
        if (cards.isEmpty()) {
            gameStatus = GameStatus.WON
        }
    }

    private fun clearSelection() {
        selectedCards = emptyList()
        isCheckingMatch = false
    }

    // Update timer
    fun decrementTime() {
        if (gameStatus == GameStatus.PLAYING && timeLeft > 0) {
            timeLeft--
            if (timeLeft == 0 && cards.isNotEmpty()) {
                gameStatus = GameStatus.LOST
            }
        }
    }

    // Check if card should be face up
    fun isCardFaceUp(card: CardData): Boolean {
        return selectedCards.any { it.id == card.id }
    }
}
