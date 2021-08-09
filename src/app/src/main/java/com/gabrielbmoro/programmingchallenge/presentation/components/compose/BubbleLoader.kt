package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BubbleLoader(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier) {
        val circleDiameter = 56.dp.toPx()
        val circleRadius = circleDiameter / 2f

        val firstCirclePosition = (size.width / 2f) - circleDiameter

        drawCircle(color, circleRadius, center = Offset(firstCirclePosition, 0f))
        drawCircle(color, circleRadius, center = Offset(firstCirclePosition + circleDiameter, 0f))
        drawCircle(color, circleRadius, center = Offset(firstCirclePosition + (circleDiameter * 2), 0f))
    }
}