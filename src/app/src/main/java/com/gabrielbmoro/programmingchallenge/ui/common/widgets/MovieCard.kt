package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.core.di.ConfigVariables

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieCard(
    imageUrl: String?,
    title: String,
    votes: Float,
    onClick: (() -> Unit)
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 3.dp,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.heightIn(
                max = dimensionResource(R.dimen.card_view_height)
            )
        ) {
            MovieImage(
                imageUrl = imageUrl?.let { "${ConfigVariables.SMALL_SIZE_IMAGE_ADDRESS}$imageUrl" },
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            MovieCardInformation(
                title = title,
                votes = votes,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}