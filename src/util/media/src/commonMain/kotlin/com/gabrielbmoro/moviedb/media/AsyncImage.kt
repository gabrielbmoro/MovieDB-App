package com.gabrielbmoro.moviedb.media

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.ImageRequest

@Suppress("LongParameterList")
@Composable
fun AsyncImage(
    imageUrl: String,
    contentDescription: String?,
    contentScale: ContentScale,
    onFailure: (@Composable () -> Unit) = {},
    filterQuality: FilterQuality = FilterQuality.High,
    modifier: Modifier = Modifier
) {
    val platformContext = LocalPlatformContext.current
    val imageRequest = remember {
        ImageRequest.Builder(
            context = platformContext
        ).data(
            imageUrl
        ).fetcherFactory(
            KtorNetworkFetcherFactory()
        ).build()
    }

    SubcomposeAsyncImage(
        model = imageRequest,
        filterQuality = filterQuality,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        loading = {
            Box {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                        .heightIn(max = 52.dp)
                        .widthIn(max = 52.dp)
                )
            }
        },
        error = {
            onFailure()
        }
    )
}
