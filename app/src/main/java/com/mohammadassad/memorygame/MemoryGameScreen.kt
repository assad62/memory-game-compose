package com.mohammadassad.memorygame

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mohammadassad.memorygame.ui.GameGrid
import com.mohammadassad.memorygame.ui.GameOverDialog
import com.mohammadassad.memorygame.ui.GameTopBar
import kotlinx.coroutines.delay

@Composable
fun MemoryGameScreen() {
    val gameState = remember { MemoryGameState() }

    // Initialize game on first composition
    LaunchedEffect(Unit) {
        gameState.initializeGame()
    }

    // Timer countdown
    LaunchedEffect(gameState.gameStatus) {
        while (gameState.gameStatus == GameStatus.PLAYING) {
            delay(GameConstants.TIMER_INTERVAL_MS)
            gameState.decrementTime()
        }
    }

    // Check for match when 2 cards selected
    LaunchedEffect(gameState.selectedCards.size) {
        if (gameState.selectedCards.size == 2) {
            gameState.checkMatch()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            GameTopBar(
                timeLeft = gameState.timeLeft,
                score = gameState.score,
                onRestart = { gameState.initializeGame() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GameGrid(
                cards = gameState.cards,
                onCardClick = { gameState.onCardClick(it) },
                isCardFaceUp = { gameState.isCardFaceUp(it) }
            )

            // Game Over Dialog
            if (gameState.gameStatus != GameStatus.PLAYING) {
                GameOverDialog(
                    isWon = gameState.gameStatus == GameStatus.WON,
                    score = gameState.score,
                    onRestart = { gameState.initializeGame() }
                )
            }
        }
    }
}
