package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.programmingchallenge.presentation.detailedScreen.MovieDetailedActivity
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie

@Composable
fun MovieListScreen(moviesState: State<List<Movie>?>, requestMoreElementsCallback: (() -> Unit)) {
    val movies = moviesState.value

    val context = LocalContext.current

    if (movies.isNullOrEmpty()) {
        EmptyState(modifier = Modifier.padding(22.dp))
    } else {
        val listState = rememberLazyListState()

        if (listState.firstVisibleItemIndex == movies.lastIndex - 2) {
            requestMoreElementsCallback.invoke()
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
}