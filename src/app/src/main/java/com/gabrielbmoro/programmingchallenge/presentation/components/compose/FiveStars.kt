package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.gabrielbmoro.programmingchallenge.R
import kotlin.math.roundToInt

private const val STARS_AVAILABLE = 5
private const val AVERAGE_TOTAL = 10
private const val INVALID_NUMBER = -1f

@DrawableRes
private fun getDrawableAccordingToStarPosition(votes: Float, position: Int): Int {
    return when {
        votes >= position -> R.drawable.ic_star
        votes < position -> {
            if (votes.roundToInt() == position)
                R.drawable.ic_star_half
            else
                R.drawable.ic_star_border
        }
        else -> R.drawable.ic_star_border
    }
}

@Composable
fun FiveStars(votes: Float) {
    val numberOfStars = (votes / AVERAGE_TOTAL) * STARS_AVAILABLE
    if (votes != INVALID_NUMBER) {
        Row {
            Image(
                painter = painterResource(
                    id = getDrawableAccordingToStarPosition(
                        numberOfStars, 0
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter = painterResource(
                    id = getDrawableAccordingToStarPosition(
                        numberOfStars, 1
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter = painterResource(
                    id = getDrawableAccordingToStarPosition(
                        numberOfStars, 2
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter = painterResource(
                    id = getDrawableAccordingToStarPosition(
                        numberOfStars, 3
                    )
                ),
                contentDescription = ""
            )
            Image(
                painter = painterResource(
                    id = getDrawableAccordingToStarPosition(
                        numberOfStars, 4
                    )
                ),
                contentDescription = ""
            )
        }
    }
}