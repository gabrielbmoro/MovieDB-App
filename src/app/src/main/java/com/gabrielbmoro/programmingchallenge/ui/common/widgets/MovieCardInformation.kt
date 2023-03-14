package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun MovieCardInformation(title: String, votes: Float, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.09f))
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp, start = 4.dp, end = 4.dp)
                .wrapContentWidth(Alignment.Start),
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        FiveStars(
            votes = votes,
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(bottom = 12.dp, start = 4.dp, end = 4.dp)
        )
    }
}