package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

    if (firstVisibleItemIndex == movies.lastIndex - 4) {
        requestMoreCallback.invoke()
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            itemContent = {
                val movie = movies[it]

                MovieCard(
                    imageUrl = movie.posterImageUrl,
                    title = movie.title,
                    votes = movie.votesAverage,
                    description = movie.overview,
                    onClick = { onSelectMovie(movie) }
                )
            },
            count = movies.size
        )
    }
}