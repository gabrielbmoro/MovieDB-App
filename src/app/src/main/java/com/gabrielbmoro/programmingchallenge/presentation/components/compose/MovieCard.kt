package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun MovieCard(
    imageUrl: String?,
    title: String,
    releaseDate: String,
    votes: Float,
    onClick: (() -> Unit)
) {
    Card(
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            Modifier.clickable {
                onClick.invoke()
            }
        ) {
            MovieImage(imageUrl = imageUrl, ContentScale.Fit)
            Column {
                MovieCardInformation(
                    title = title,
                    releaseDate = releaseDate
                )
                FiveStars(votes = votes)
            }
        }
    }
}