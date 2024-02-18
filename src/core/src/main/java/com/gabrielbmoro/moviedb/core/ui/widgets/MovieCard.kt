package com.gabrielbmoro.moviedb.core.ui.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.core.R
import kotlinx.coroutines.launch

const val threshold = -120f

@Composable
fun MovieCard(
    imageUrl: String?,
    title: String,
    description: String,
    votes: Float,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)
) {
    val offsetX = remember { Animatable(0f) }
    val draggableState = rememberSwipeState(offsetX)

    Box(modifier = modifier) {

        Button(modifier = Modifier.align(Alignment.CenterEnd), onClick = {}) {
            Text("Delete")
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .height(dimensionResource(id = R.dimen.card_view_image_height))
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal
                )
                .offset(x = offsetX.targetValue.dp)
                .clickable(onClick = onClick)
        ) {
            Row {
                MovieImage(
                    imageUrl = imageUrl,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.poster_card_width))
                        .fillMaxHeight(),
                    contentDescription = stringResource(id = R.string.poster)
                )
                MovieCardInformation(
                    title = title,
                    votes = votes,
                    modifier = Modifier
                        .fillMaxSize(),
                    description = description
                )
            }
        }
    }
}

@Composable
private fun rememberSwipeState(offsetX: Animatable<Float, AnimationVector1D>): DraggableState {
    val coroutineScope = rememberCoroutineScope()
    return rememberDraggableState(
        onDelta = { delta ->
            val targetOffsetX = offsetX.value + delta
            if (targetOffsetX < 0f) { // swipe to the left
                if (targetOffsetX < threshold) {
                    coroutineScope.launch {
                        offsetX.animateTo(threshold)
                    }
                } else {
                    coroutineScope.launch {
                        offsetX.snapTo(targetOffsetX)
                    }
                }
            } else {
                coroutineScope.launch {
                    offsetX.animateTo(
                        targetValue = 0f, animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                }
            }
        }
    )
}
