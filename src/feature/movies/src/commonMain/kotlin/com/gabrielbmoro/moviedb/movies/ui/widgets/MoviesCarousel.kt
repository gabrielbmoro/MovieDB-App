package com.gabrielbmoro.moviedb.movies.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabrielbmoro.moviedb.desingsystem.images.MovieImage
import com.gabrielbmoro.moviedb.domain.entities.Movie

private const val windowSize = 5

@Composable
fun MoviesCarousel(
    title: String,
    movies: List<Movie>,
    onSelectMovie: ((Movie) -> Unit),
    onRequestMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val firstVisibleItemIndex by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex
        }
    }

    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = 18.sp
        )
    )

    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
    )

    LazyRow(
        state = lazyListState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            items(
                count = movies.size,
                key = { index ->
                    movies[index].id
                },
            ) { index ->
                val movie = movies[index]
                MovieImage(
                    imageUrl = movie.posterImageUrl,
                    contentScale = ContentScale.FillHeight,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .width(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectMovie(movie) }
                        .fillMaxHeight()
                )
            }
        }
    )


    LaunchedEffect(key1 = firstVisibleItemIndex) {
        val firstItemIndexOfLastWindow = movies.lastIndex - windowSize
        val reachTheBeginningOfTheLastWindow = firstVisibleItemIndex == firstItemIndexOfLastWindow

        if (reachTheBeginningOfTheLastWindow) {
            onRequestMore()
        }
    }
}
