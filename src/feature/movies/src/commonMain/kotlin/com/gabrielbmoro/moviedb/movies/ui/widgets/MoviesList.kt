package com.gabrielbmoro.moviedb.movies.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.desingsystem.images.MovieImage
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieCardInfo
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MoviesList(
    movies: ImmutableList<MovieCardInfo>,
    lazyStaggeredGridState: LazyStaggeredGridState,
    onSelectMovie: ((Long) -> Unit),
    onRequestMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val canScrollForward by remember {
        derivedStateOf {
            lazyStaggeredGridState.canScrollForward
        }
    }

    LazyVerticalStaggeredGrid(
        state = lazyStaggeredGridState,
        modifier = modifier,
        columns = StaggeredGridCells.Adaptive(120.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(
                count = movies.size,
                key = { index ->
                    movies[index].movieId
                },
            ) { index ->
                val movieCardInfo = movies[index]
                MovieImage(
                    imageUrl = movieCardInfo.moviePosterUrl,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = movieCardInfo.movieTitle,
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectMovie(movieCardInfo.movieId) },
                )
            }
        },
    )

    LaunchedEffect(key1 = canScrollForward) {
        if (canScrollForward.not()) {
            onRequestMore()
        }
    }
}
