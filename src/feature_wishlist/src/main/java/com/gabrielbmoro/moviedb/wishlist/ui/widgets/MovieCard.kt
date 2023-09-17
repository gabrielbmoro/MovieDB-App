package com.gabrielbmoro.moviedb.wishlist.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.core.R
import com.gabrielbmoro.moviedb.core.ui.widgets.MovieImage

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
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .height(dimensionResource(id = R.dimen.card_view_image_height))
    ) {
        Row {
            MovieImage(
                imageUrl = imageUrl,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.poster_card_width))
                    .fillMaxHeight(),
                contentDescription = stringResource(id = R.string.poster)
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
