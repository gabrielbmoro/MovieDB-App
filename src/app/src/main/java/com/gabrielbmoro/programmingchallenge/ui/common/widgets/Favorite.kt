package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gabrielbmoro.programmingchallenge.R

@Composable
fun Favorite(isFavorite: Boolean, modifier: Modifier = Modifier, action: (() -> Unit)) {
    val heartPair = if (!isFavorite) Pair(
        R.drawable.ic_heart_border,
        stringResource(id = R.string.alt_is_not_favorite)
    )
    else Pair(
        R.drawable.ic_heart_filled,
        stringResource(id = R.string.alt_is_favorite)
    )

    Card(
        shape = CircleShape,
        modifier = modifier.clickable(onClick = action)
    ) {
        Icon(
            painter = painterResource(id = heartPair.first),
            contentDescription = heartPair.second
        )
    }
}