package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Composable
private fun MovieListInternal(
    itemFactory: (LazyListScope.() -> Unit),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemFactory()
    }
}

@Composable
fun MoviesListPaginated(
    pagingDataFlow: Flow<PagingData<Movie>>,
    onSelectMovie: ((Movie) -> Unit),
    modifier: Modifier = Modifier
) {
    val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()

    MovieListInternal(
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

@Composable
fun MovieList(
    movies: List<Movie>,
    onSelectMovie: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    MovieListInternal(
        modifier = modifier,
        itemFactory = {
            items(
                count = movies.size,
            ) { index ->

                movies[index].let { movie ->
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