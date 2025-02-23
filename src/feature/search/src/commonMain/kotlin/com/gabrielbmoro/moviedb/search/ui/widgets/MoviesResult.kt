package com.gabrielbmoro.moviedb.search.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.desingsystem.cards.MovieCard
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MoviesResult(
    movies: ImmutableList<MovieCardInfo>,
    navigateToDetailsScreen: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        items(movies.size) {
            val moviecardInfo = movies[it]

            MovieCard(
                imageUrl = moviecardInfo.moviePosterImageUrl,
                title = moviecardInfo.movieTitle,
                description = moviecardInfo.movieOverview,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navigateToDetailsScreen(moviecardInfo.movieId)
                },
                votes = moviecardInfo.movieVotesAverage,
            )
        }
    }
}

@Immutable
@Stable
data class MovieCardInfo(
    val movieId: Long,
    val moviePosterImageUrl: String,
    val movieTitle: String,
    val movieOverview: String,
    val movieVotesAverage: Float,
)
