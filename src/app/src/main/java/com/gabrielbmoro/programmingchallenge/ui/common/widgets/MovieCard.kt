package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.core.di.ConfigVariables

@Composable
fun MovieCard(
    imageUrl: String?,
    title: String,
    description: String,
    votes: Float,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(dimensionResource(id = R.dimen.card_view_image_height))
    ) {
        Row {
            MovieImage(
                imageUrl = imageUrl?.let { "${ConfigVariables.SMALL_SIZE_IMAGE_ADDRESS}$imageUrl" },
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.poster_card_width))
                    .fillMaxHeight()
            )
            MovieCardInformation(
                title = title,
                votes = votes,
                modifier = Modifier
                    .fillMaxSize(),
                description = description
            )
        }
    }
}