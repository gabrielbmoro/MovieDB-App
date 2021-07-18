package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.rememberImagePainter
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.core.di.ConfigVariables

@Composable
fun MovieImage(imageUrl: String?, modifier: Modifier = Modifier, contentScale: ContentScale) {
    imageUrl ?: return
    Image(
        painter = rememberImagePainter(
            "${ConfigVariables.BASE_IMAGE_ADDRESS}$imageUrl"
        ),
        contentScale = contentScale,
        contentDescription = stringResource(id = R.string.poster),
        modifier = modifier
    )
}