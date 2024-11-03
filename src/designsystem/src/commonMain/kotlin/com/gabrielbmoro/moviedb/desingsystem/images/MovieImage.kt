package com.gabrielbmoro.moviedb.desingsystem.images

import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.gabrielbmoro.moviedb.SharedRes
import com.gabrielbmoro.moviedb.media.AsyncImage
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun MovieImage(
    imageUrl: String?,
    contentDescription: String,
    contentScale: ContentScale,
    modifier: Modifier = Modifier
) {
    if (imageUrl != null) {
        AsyncImage(
            imageUrl = imageUrl,
            contentScale = contentScale,
            contentDescription = contentDescription,
            modifier = modifier,
            onLoading = { CircularProgressIndicator({ it }) },
            onFailure = { exception: Throwable ->
                exception.printStackTrace()
            }
        )
    } else {
        Image(
            painter = painterResource(SharedRes.images.ic_movie_media_player),
            contentScale = contentScale,
            alignment = Alignment.TopCenter,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}
