package com.gabrielbmoro.moviedb.search.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.core.ui.widgets.MovieCard
import com.gabrielbmoro.moviedb.domain.entities.Movie

@Composable
fun MoviesResult(
    movies: List<Movie>,
    navigateToDetailsScreen: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(movies.size) {
            val movie = movies[it]

            MovieCard(
                imageUrl = movie.posterImageUrl,
                title = movie.title,
                description = movie.overview,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navigateToDetailsScreen(movie)
                },
                votes = movie.votesAverage
            )
        }
    }
}
