package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.gabrielbmoro.moviedb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Composable
private fun MovieListInternal(
    lazyListState: LazyListState,
    itemFactory: (LazyListScope.() -> Unit),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemFactory()
    }
}

@Composable
fun BoxScope.MoviesListPaginated(
    lazyListState: LazyListState,
    pagingDataFlow: Flow<PagingData<Movie>>,
    onSelectMovie: ((Movie) -> Unit),
    modifier: Modifier = Modifier
) {
    val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()

    if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
        BubbleLoader(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.secondary
        )
    } else {
        when (lazyPagingItems.itemCount) {
            0 -> {
                EmptyState(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            else -> {
                MovieListInternal(
                    lazyListState = lazyListState,
                    modifier = modifier,
                    itemFactory = {
                        items(
                            count = lazyPagingItems.itemCount,
                            key = lazyPagingItems.itemKey(),
                            contentType = lazyPagingItems.itemContentType()
                        ) { index ->
                            lazyPagingItems[index]?.let { movie ->
                                MovieCard(
                                    imageUrl = movie.posterImageUrl,
                                    title = movie.title,
                                    votes = movie.votesAverage,
                                    description = movie.overview,
                                    onClick = { onSelectMovie(movie) }
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BoxScope.MovieList(
    movies: List<Movie>?,
    lazyListState: LazyListState,
    isLoading: Boolean,
    onSelectMovie: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        BubbleLoader(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.secondary
        )
    } else {
        when {
            movies?.isEmpty() == true -> {
                EmptyState(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            else -> {
                val moviesList = movies ?: emptyList()

                MovieListInternal(
                    modifier = modifier,
                    lazyListState = lazyListState,
                    itemFactory = {
                        items(
                            count = moviesList.size,
                        ) { index ->

                            moviesList[index].let { movie ->
                                MovieCard(
                                    imageUrl = movie.posterImageUrl,
                                    title = movie.title,
                                    votes = movie.votesAverage,
                                    description = movie.overview,
                                    onClick = { onSelectMovie(movie) }
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}