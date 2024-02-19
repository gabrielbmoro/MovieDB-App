package com.gabrielbmoro.moviedb.core.ui.widgets

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.core.R

private const val THRESHOLD_VALUE = -100

enum class DragValue { Start, None }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCard(
    imageUrl: String?,
    title: String,
    description: String,
    votes: Float,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit),
    enableSwipeToDelete: Boolean = false,
    onDeleteClick: (() -> Unit) = {}
) {
    val density = LocalDensity.current

    val anchors = DraggableAnchors {
        DragValue.Start at with(density) { THRESHOLD_VALUE.dp.toPx() }
        DragValue.None at 0f
    }

    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = DragValue.None,
            anchors = anchors,
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            positionalThreshold = { totalDistance -> totalDistance * 0.5f },
            animationSpec = tween()
        ).apply { updateAnchors(anchors) }
    }

    Box(modifier = modifier) {
        DeleteButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        )

        Card(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.card_view_image_height))
                .offset {
                    IntOffset(
                        x = anchoredDraggableState
                            .requireOffset()
                            .toInt(),
                        y = 0
                    )
                }
                .anchoredDraggable(
                    state = anchoredDraggableState,
                    enabled = enableSwipeToDelete,
                    orientation = Orientation.Horizontal
                ),
            shape = RoundedCornerShape(12.dp),
            onClick = onClick
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
