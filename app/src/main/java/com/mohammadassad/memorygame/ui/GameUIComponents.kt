package com.mohammadassad.memorygame.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohammadassad.memorygame.*

/**
 * Reusable Game Top Bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    timeLeft: Int,
    score: Int,
    onRestart: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GameTimer(timeLeft = timeLeft)
                ScoreDisplay(score = score)
            }
        },
        actions = {
            IconButton(onClick = onRestart) {
                Icon(Icons.Filled.Refresh, contentDescription = "Restart")
            }
        }
    )
}

/**
 * Reusable Timer Display
 */
@Composable
fun GameTimer(timeLeft: Int) {
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeColor = if (timeLeft <= GameConstants.TIME_WARNING_THRESHOLD) 
        MaterialTheme.colorScheme.error
    else 
        MaterialTheme.colorScheme.onSurface

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Filled.DateRange,
            contentDescription = null,
            tint = timeColor,
            modifier = Modifier.size(GameConstants.ICON_SIZE_DP.dp)
        )
        Spacer(modifier = Modifier.width(GameConstants.SPACING_DP.dp))
        Text(
            text = String.format("%02d:%02d", minutes, seconds),
            style = MaterialTheme.typography.titleMedium,
            color = timeColor,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Reusable Score Display
 */
@Composable
fun ScoreDisplay(score: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Filled.ThumbUp,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(GameConstants.ICON_SIZE_DP.dp)
        )
        Spacer(modifier = Modifier.width(GameConstants.SPACING_DP.dp))
        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Memory Card using extracted FlipAnimation
 */
@Composable
fun MemoryCard(
    card: CardData,
    isFlipped: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(GameConstants.CARD_PADDING_DP.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        FlipAnimation(
            isFlipped = isFlipped,
            onFlip = onClick,
            modifier = Modifier.fillMaxSize(),
            durationMillis = GameConstants.FLIP_ANIMATION_DURATION_MS,
            frontSide = {
                CardBack()
            },
            backSide = {
                CardFront(card = card)
            }
        )
    }
}

@Composable
private fun CardBack() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Filled.Lock,
            contentDescription = "Hidden card",
            modifier = Modifier.size(GameConstants.CARD_ICON_SIZE_DP.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun CardFront(card: CardData) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            card.iconType,
            contentDescription = "Card icon",
            modifier = Modifier.size(GameConstants.CARD_ICON_SIZE_DP.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

/**
 * Reusable Game Over Dialog
 */
@Composable
fun GameOverDialog(
    isWon: Boolean,
    score: Int,
    onRestart: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = if (isWon) "🎉 You Won!" else "⏰ Time's Up!",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (isWon)
                        "Congratulations! You matched all pairs!"
                    else
                        "Better luck next time!",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(GameConstants.GRID_SPACING_DP.dp))
                Text(
                    text = "Final Score: $score",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            Button(onClick = onRestart) {
                Text("Play Again")
            }
        }
    )
}

/**
 * Game Grid Component
 */
@Composable
fun GameGrid(
    cards: List<CardData>,
    onCardClick: (CardData) -> Unit,
    isCardFaceUp: (CardData) -> Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(GameConstants.GRID_COLUMNS),
        modifier = Modifier
            .fillMaxSize()
            .padding(GameConstants.GRID_SPACING_DP.dp),
        verticalArrangement = Arrangement.spacedBy(GameConstants.GRID_SPACING_DP.dp),
        horizontalArrangement = Arrangement.spacedBy(GameConstants.GRID_SPACING_DP.dp)
    ) {
        items(cards) { card ->
            MemoryCard(
                card = card,
                isFlipped = isCardFaceUp(card),
                onClick = { onCardClick(card) }
            )
        }
    }
}
