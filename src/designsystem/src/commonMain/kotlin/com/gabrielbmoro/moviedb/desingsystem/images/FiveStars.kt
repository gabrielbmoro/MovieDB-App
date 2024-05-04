package com.gabrielbmoro.moviedb.desingsystem.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.SharedRes
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

private const val STARS_AVAILABLE = 5
private const val AVERAGE_TOTAL = 10
private const val INVALID_NUMBER = -1f

private fun getDrawableAccordingToStarPosition(
    votes: Float,
    position: Int
): ImageResource {
    return when {
        votes >= position -> SharedRes.images.ic_star
        votes < position -> {
            if (votes.toInt() == position) {
                SharedRes.images.ic_star_half
            } else {
                SharedRes.images.ic_star_border
            }
        }

        else -> SharedRes.images.ic_star_border
    }
}

@Composable
fun FiveStars(
    votes: Float,
    modifier: Modifier = Modifier
) {
    val numberOfStars = (votes / AVERAGE_TOTAL) * STARS_AVAILABLE
    if (votes != INVALID_NUMBER) {
        Row(modifier = modifier.heightIn(max = 56.dp)) {
            Image(
                painter =
                painterResource(
                    getDrawableAccordingToStarPosition(
                        numberOfStars,
                        0
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter =
                painterResource(
                    getDrawableAccordingToStarPosition(
                        numberOfStars,
                        1
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter =
                painterResource(
                    getDrawableAccordingToStarPosition(
                        numberOfStars,
                        2
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter =
                painterResource(
                    getDrawableAccordingToStarPosition(
                        numberOfStars,
                        3
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter =
                painterResource(
                    getDrawableAccordingToStarPosition(
                        numberOfStars,
                        4
                    )
                ),
                contentDescription = ""
            )
        }
    }
}
