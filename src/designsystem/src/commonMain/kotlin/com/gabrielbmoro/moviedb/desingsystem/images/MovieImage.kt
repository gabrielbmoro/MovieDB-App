package com.gabrielbmoro.moviedb.desingsystem.images

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.gabrielbmoro.moviedb.media.AsyncImage
import moviedbapp.designsystem.generated.resources.Res
import moviedbapp.designsystem.generated.resources.ic_movie_media_player
import org.jetbrains.compose.resources.painterResource

@Composable
fun MovieImage(
    imageUrl: String?,
    contentDescription: String,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    if (imageUrl != null) {
        AsyncImage(
            imageUrl = imageUrl,
            contentScale = contentScale,
            contentDescription = contentDescription,
            modifier = modifier,
        )
    } else {
        Image(
            painter = painterResource(Res.drawable.ic_movie_media_player),
            contentScale = contentScale,
            alignment = Alignment.TopCenter,
            contentDescription = contentDescription,
            modifier = modifier,
        )
    }
}
