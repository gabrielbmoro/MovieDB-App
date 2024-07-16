@file:Suppress("LongParameterList", "LongMethod")

package com.gabrielbmoro.moviedb.desingsystem.cards

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.SharedRes
import com.gabrielbmoro.moviedb.desingsystem.buttons.DeleteButton
import com.gabrielbmoro.moviedb.desingsystem.images.MovieImage
import dev.icerock.moko.resources.compose.stringResource

private const val THRESHOLD_VALUE = -230

val CardViewImageHeight = 200.dp
val PosterCardWidth = 120.dp

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
    val anchors =
        DraggableAnchors {
            DragValue.Start at THRESHOLD_VALUE.dp.value
            DragValue.None at 0f
        }

    val anchoredDraggableState =
        remember {
            AnchoredDraggableState(
                initialValue = DragValue.None,
                anchors = anchors,
                velocityThreshold = { THRESHOLD_VALUE.times(-1).dp.value },
                positionalThreshold = { totalDistance -> totalDistance * 0.5f },
                animationSpec =
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            )
        }

    LaunchedEffect(Unit) {
        anchoredDraggableState.updateAnchors(anchors)
    }

    Box(modifier = modifier) {
        DeleteButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        )

        Card(
            modifier =
            Modifier
                .height(CardViewImageHeight)
                .offset {
                    IntOffset(
                        x =
                        anchoredDraggableState
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
                    modifier =
                    Modifier
                        .width(PosterCardWidth)
                        .fillMaxHeight(),
                    contentDescription = stringResource(SharedRes.strings.poster)
                )
                MovieCardInformation(
                    title = title,
                    votes = votes,
                    modifier =
                    Modifier
                        .fillMaxSize(),
                    description = description
                )
            }
        }
    }
}
