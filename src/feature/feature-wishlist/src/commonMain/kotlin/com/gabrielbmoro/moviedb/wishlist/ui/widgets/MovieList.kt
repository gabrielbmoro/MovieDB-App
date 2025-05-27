package com.gabrielbmoro.moviedb.wishlist.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.desingsystem.cards.MovieCard
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MovieList(
    moviesList: ImmutableList<MovieCardInfo>,
    onSelectMovie: (MovieCardInfo) -> Unit,
    onDeleteMovie: (MovieCardInfo) -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
    ) {
        items(
            count = moviesList.size,
            key = { index ->
                moviesList[index].id
            },
        ) { index ->
            val movie = moviesList[index]
            MovieCard(
                imageUrl = movie.posterImageUrl,
                title = movie.title,
                votes = movie.votesAverage,
                description = movie.overview,
                onClick = { onSelectMovie(movie) },
                enableDelete = true,
                onDeleteClick = { onDeleteMovie(movie) },
            )
        }
    }
}

@Stable
@Immutable
data class MovieCardInfo(
    val id: Long,
    val title: String,
    val votesAverage: Float,
    val overview: String,
    val posterImageUrl: String?,
)
