package com.gabrielbmoro.moviedb.details.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gabrielbmoro.moviedb.core.ui.theme.MovieDBAppTheme
import com.gabrielbmoro.moviedb.core.ui.theme.ThemePreviews
import com.gabrielbmoro.moviedb.core.ui.widgets.FiveStars

@Composable
fun MovieDetailIndicator(
    isFavorite: Boolean,
    votesAverage: Float,
    onFavoriteMovie: ((Boolean) -> Unit),
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
            isFavorite = isFavorite
        ) {
            onFavoriteMovie(isFavorite.not())
        }
        FiveStars(
            votes = votesAverage,
        )
    }
}

@ThemePreviews
@Composable
fun MovieDetailIndicatorPreview() {
    MovieDBAppTheme {
        MovieDetailIndicator(
            isFavorite = false,
            votesAverage = 10f,
            onFavoriteMovie = {}
        )
    }
}