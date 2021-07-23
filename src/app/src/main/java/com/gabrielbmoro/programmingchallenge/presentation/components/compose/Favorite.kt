package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gabrielbmoro.programmingchallenge.R

@Composable
fun Favorite(isFavorite: State<Boolean>, modifier: Modifier = Modifier, action: (() -> Unit)) {
    val heartPair = if (!isFavorite.value) Pair(
        R.drawable.ic_heart_border,
        stringResource(id = R.string.alt_is_not_favorite)
    )
    else Pair(
        R.drawable.ic_heart_filled,
        stringResource(id = R.string.alt_is_favorite)
    )

    Image(
        painter = painterResource(id = heartPair.first),
        contentDescription = heartPair.second,
        modifier = (modifier).clickable(
            enabled = true,
            onClick = {
                action.invoke()
            }
        )
    )
}