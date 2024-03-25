package com.gabrielbmoro.moviedb.details.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gabrielbmoro.moviedb.desingsystem.images.FiveStars

@Composable
fun MovieDetailIndicator(
    isFavorite: Boolean,
    votesAverage: Float,
    onFavoriteMovie: (() -> Unit),
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Favorite(
            isFavorite = isFavorite,
            action = onFavoriteMovie
        )

        FiveStars(
            votes = votesAverage
        )
    }
}
