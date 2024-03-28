package com.gabrielbmoro.moviedb.desingsystem.loaders

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
private fun Bubble(
    modifier: Modifier = Modifier,
    color: Color,
    startFractionValue: Float,
    targetFractionValue: Float,
    delayTimeMillis: Int,
    durationTimeMillis: Int,
) {
    val animatedValue = remember { Animatable(startFractionValue) }

    val bubbleSize = 32.dp * animatedValue.value
    val bubbleRadius: Dp = (bubbleSize / 2f)

    Canvas(modifier.size(bubbleSize)) {
        drawCircle(
            color = color,
            center = Offset(bubbleRadius.toPx(), 0f),
            radius = bubbleRadius.toPx(),
        )
    }

    LaunchedEffect(animatedValue) {
        animatedValue.animateTo(
            targetFractionValue,
            animationSpec =
                infiniteRepeatable(
                    animation =
                        tween(
                            delayMillis = delayTimeMillis,
                            durationMillis = durationTimeMillis,
                            easing = FastOutSlowInEasing,
                        ),
                ),
        )
    }
}

@Composable
fun BubbleLoader(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Row(modifier) {
        Bubble(
            color = color,
            durationTimeMillis = 1200,
            delayTimeMillis = 0,
            startFractionValue = 0f,
            targetFractionValue = 1f,
        )
        Bubble(
            modifier = Modifier.padding(horizontal = 8.dp),
            color = color,
            durationTimeMillis = 900,
            delayTimeMillis = 300,
            startFractionValue = 0f,
            targetFractionValue = 1f,
        )
        Bubble(
            color = color,
            durationTimeMillis = 600,
            delayTimeMillis = 600,
            startFractionValue = 0f,
            targetFractionValue = 1f,
        )
    }
}
