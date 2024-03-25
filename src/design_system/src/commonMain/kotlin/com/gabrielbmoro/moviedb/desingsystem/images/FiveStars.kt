package com.gabrielbmoro.moviedb.desingsystem.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moviedb_android.design_system.generated.resources.Res
import moviedb_android.design_system.generated.resources.ic_star
import moviedb_android.design_system.generated.resources.ic_star_border
import moviedb_android.design_system.generated.resources.ic_star_half
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private const val STARS_AVAILABLE = 5
private const val AVERAGE_TOTAL = 10
private const val INVALID_NUMBER = -1f

@OptIn(ExperimentalResourceApi::class)
private fun getDrawableAccordingToStarPosition(votes: Float, position: Int): DrawableResource {
    return when {
        votes >= position -> Res.drawable.ic_star
        votes < position -> {
            if (votes.toInt() == position) {
                Res.drawable.ic_star_half
            } else {
                Res.drawable.ic_star_border
            }
        }

        else -> Res.drawable.ic_star_border
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FiveStars(votes: Float, modifier: Modifier = Modifier) {
    val numberOfStars = (votes / AVERAGE_TOTAL) * STARS_AVAILABLE
    if (votes != INVALID_NUMBER) {
        Row(modifier = modifier.heightIn(max = 56.dp)) {
            Image(
                painter = painterResource(
                    getDrawableAccordingToStarPosition(
                        numberOfStars,
                        0
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter = painterResource(
                    getDrawableAccordingToStarPosition(
                        numberOfStars,
                        1
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter = painterResource(
                    getDrawableAccordingToStarPosition(
                        numberOfStars,
                        2
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter = painterResource(
                    getDrawableAccordingToStarPosition(
                        numberOfStars,
                        3
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter = painterResource(
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