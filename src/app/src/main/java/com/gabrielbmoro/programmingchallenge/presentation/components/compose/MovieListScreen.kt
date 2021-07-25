package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.State
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie

@Composable
fun MovieListScreen(moviesState: State<List<Movie>?>) {
    val movies = moviesState.value

    if (movies.isNullOrEmpty()) {
        EmptyState()
    } else {
        LazyColumn() {
            items(movies) { movie ->
                MovieCard(
                    imageUrl = movie.posterPath,
                    title = movie.title ?: "",
                    releaseDate = movie.releaseDate ?: "",
                    votes = movie.votesAverage ?: 0f
                ) {

                }
            }
        }
    }
}