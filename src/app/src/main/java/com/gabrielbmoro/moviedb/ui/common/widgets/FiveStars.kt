package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.R
import com.gabrielbmoro.moviedb.ui.common.theme.ThemePreviews
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
fun FiveStars(votes: Float, modifier: Modifier = Modifier) {
    val numberOfStars = (votes / AVERAGE_TOTAL) * STARS_AVAILABLE
    if (votes != INVALID_NUMBER) {
        Row(modifier = modifier.heightIn(max = 56.dp)) {
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

@ThemePreviews
@Composable
fun FiveStarsPreview() {
    FiveStars(votes = 5f)
}