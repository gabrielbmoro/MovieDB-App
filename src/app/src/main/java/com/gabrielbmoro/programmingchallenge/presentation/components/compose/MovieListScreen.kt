package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.programmingchallenge.presentation.detailedScreen.MovieDetailedActivity
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie

@Composable
private fun MoviesList(
    movies: List<Movie>,
    navArg: NavigationArgument,
    context: Context
) {
    val listState = rememberLazyListState()

    if (listState.firstVisibleItemIndex == movies.lastIndex - 2) {
        navArg.requestMore.invoke()
    }

    LazyColumn(state = listState) {
        items(movies) { movie ->
            MovieCard(
                imageUrl = movie.posterPath,
                title = movie.title ?: "",
                releaseDate = movie.releaseDate ?: "",
                votes = movie.votesAverage ?: 0f
            ) {
                context.startActivity(
                    MovieDetailedActivity.newIntent(context, movie)
                )
            }
        }
    }
}

@Composable
fun MovieListScreen(navArg: NavigationArgument) {
    val movies = navArg.moviesState.value

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (movies.isNullOrEmpty()) {
            EmptyState()
        } else {
            MoviesList(movies, navArg, context)
        }

        if (navArg.loadingState.value == true) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}