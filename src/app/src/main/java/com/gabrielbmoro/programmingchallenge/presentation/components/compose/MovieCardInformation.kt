package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie

@Composable
fun MovieCardInformation(movie: Movie) {
    val textColor = if (isSystemInDarkTheme()) {
        Color.White
    } else Color.Black

    Column {
        Text(
            text = movie.title ?: "",
            style = MaterialTheme.typography.h6.copy(color = textColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .wrapContentWidth(Alignment.Start)
        )
        Text(
            text = movie.releaseDate ?: "",
            style = MaterialTheme.typography.body1.copy(color = textColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .wrapContentWidth(Alignment.Start)
        )
    }
}