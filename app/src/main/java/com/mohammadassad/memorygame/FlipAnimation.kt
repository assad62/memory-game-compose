package com.mohammadassad.memorygame

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Reusable flip animation composable
 */
@Composable
fun FlipAnimation(
    isFlipped: Boolean,
    onFlip: () -> Unit,
    modifier: Modifier = Modifier,
    durationMillis: Int = 600,
    frontSide: @Composable () -> Unit,
    backSide: @Composable () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing
        ),
        label = "flip"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable { onFlip() }
    ) {
        if (rotation <= 90f) {
            frontSide()
        } else {
            Box(
                modifier = Modifier.graphicsLayer {
                    rotationY = 180f
                }
            ) {
                backSide()
            }
        }
    }
}
