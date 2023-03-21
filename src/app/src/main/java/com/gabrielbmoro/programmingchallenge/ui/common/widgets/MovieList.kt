package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.*
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
    val firstVisibleItemIndex by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex }
    }

    if (firstVisibleItemIndex == movies.lastIndex - 2) {
        requestMoreCallback.invoke()
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        items(
            key = {
                movies[it].title
            },
            itemContent = {
                val movie = movies[it]

                MovieCard(
                    imageUrl = movie.posterImageUrl,
                    title = movie.title,
                    votes = movie.votesAverage,
                    description = movie.overview,
                    onClick = { onSelectMovie(movie) }
                )

                Box(modifier = Modifier.height(16.dp))
            },
            count = movies.size
        )
    }
}