package com.gabrielbmoro.programmingchallenge.ui.components.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
    releaseDate: String,
    votes: Float,
    onClick: (() -> Unit)
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp
    ) {
        Row(
            Modifier.clickable {
                onClick.invoke()
            }
        ) {
            MovieImage(
                imageUrl = imageUrl?.let { "${ConfigVariables.SMALL_SIZE_IMAGE_ADDRESS}$imageUrl" },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(
                        dimensionResource(R.dimen.card_view_height)
                    )
            )
            Column {
                MovieCardInformation(
                    title = title,
                    releaseDate = releaseDate,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                )
                FiveStars(
                    votes = votes,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}