package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.gabrielbmoro.moviedb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class MoviesCarouselContent(
    val sectionTitle: String,
    val movies: Flow<PagingData<Movie>>
)

@Composable
fun MoviesCarousel(
    content: MoviesCarouselContent,
    onSelectMovie: ((Movie) -> Unit),
    modifier: Modifier = Modifier,
) {
    val lazyPagingItems = content.movies.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    Text(
        text = content.sectionTitle,
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = 18.sp
        )
    )

    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
    )

    if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
        Box(modifier = modifier) {
            BubbleLoader(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        LazyRow(
            state = lazyListState,
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            content = {
                items(
                    count = lazyPagingItems.itemCount,
                    key = lazyPagingItems.itemKey(),
                    contentType = lazyPagingItems.itemContentType()
                ) { index ->
                    lazyPagingItems[index]?.let { movie ->
                        MovieImage(
                            imageUrl = movie.posterImageUrl,
                            contentScale = ContentScale.FillHeight,
                            contentDescription = movie.title,
                            modifier = Modifier
                                .width(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSelectMovie(movie) }
                                .fillMaxHeight(),
                        )
                    }
                }
            }
        )
    }
}