package com.gabrielbmoro.moviedb.feature.tvshows.ui.widgets

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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.desingsystem.images.MovieImage
import com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows.TvShowCardInfo
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ShowsList(
    shows: ImmutableList<TvShowCardInfo>,
    lazyStaggeredGridState: LazyStaggeredGridState,
    onRequestMore: () -> Unit,
    onSelectShow: ((Long) -> Unit)? = null,
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
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(
                count = shows.size,
                key = { index ->
                    shows[index].tvShowId
                },
            ) { index ->
                val tvShowCardInfo = shows[index]
                MovieImage(
                    imageUrl = tvShowCardInfo.tvShowPosterUrl,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = tvShowCardInfo.tvShowTitle,
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .let { modifier ->
                            if (onSelectShow != null) {
                                modifier
                                    .clickable { onSelectShow(tvShowCardInfo.tvShowId) }
                                    .semantics {
                                        contentDescription = tvShowCardInfo.tvShowTitle
                                    }
                            } else {
                                modifier
                            }
                        },
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
