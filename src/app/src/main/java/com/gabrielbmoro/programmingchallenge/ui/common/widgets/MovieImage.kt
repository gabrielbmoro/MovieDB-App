package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.gabrielbmoro.programmingchallenge.R

private const val DEFAULT_IMAGE =
    "https://wholefully.com/wp-content/uploads/2017/06/movie-theatre-popcorn-800x1200-720x540.jpg"

@Composable
fun MovieImage(imageUrl: String?, modifier: Modifier = Modifier, contentScale: ContentScale) {
    val url = imageUrl ?: DEFAULT_IMAGE

    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        model = url,
        imageLoader = ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build(),
        placeholder = painterResource(
            id = R.drawable.loading_image
        )
    )
    Image(
        painter = painter,
        contentScale = contentScale,
        alignment = Alignment.TopCenter,
        contentDescription = stringResource(id = R.string.poster),
        modifier = modifier
    )
}