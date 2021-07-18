package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.gabrielbmoro.programmingchallenge.core.di.ConfigVariables

@Composable
fun MovieImage(imageUrl: String?, contentScale: ContentScale) {
    imageUrl ?: return
    Image(
        painter = rememberImagePainter(
            "${ConfigVariables.BASE_IMAGE_ADDRESS}$imageUrl"
        ),
        contentScale = contentScale,
        contentDescription = null,
    )
}