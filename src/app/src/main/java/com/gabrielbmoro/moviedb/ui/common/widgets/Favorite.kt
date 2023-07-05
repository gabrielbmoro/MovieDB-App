package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.R
import com.gabrielbmoro.moviedb.ui.common.theme.ThemePreviews

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
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .clickable(onClick = action),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painterResource(id = heartPair.first),
                contentDescription = heartPair.second,
                modifier = Modifier.align(Center),
                tint = Color.Red
            )
        }
    }
}

@ThemePreviews
@Composable
fun FavoritePreview() {
    Favorite(isFavorite = false) { }
}