package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.programmingchallenge.domain.model.Movie

@Composable
fun MoviesList(
    movies: List<Movie>,
    lazyListState: LazyListState,
    requestMoreCallback: (() -> Unit),
    onSelectMovie: ((Movie) -> Unit),
    modifier: Modifier = Modifier
) {
    if (lazyListState.firstVisibleItemIndex == movies.lastIndex - 2) {
        requestMoreCallback.invoke()
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        itemsIndexed(movies) { index, movie ->
            if (index > 0) {
                Box(modifier = Modifier.height(16.dp))
            }

            MovieCard(
                imageUrl = movie.imageUrl,
                title = movie.title,
                votes = movie.votesAverage,
                onClick = { onSelectMovie(movie) }
            )
        }
    }
}