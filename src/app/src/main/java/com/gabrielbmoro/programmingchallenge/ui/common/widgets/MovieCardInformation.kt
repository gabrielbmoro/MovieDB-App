package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MovieCardInformation(title: String, releaseDate: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .wrapContentWidth(Alignment.Start),
            color = MaterialTheme.colors.onSurface,
            maxLines = 4
        )
        Text(
            text = releaseDate,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .wrapContentWidth(Alignment.Start),
            color = MaterialTheme.colors.onSurface
        )
    }
}