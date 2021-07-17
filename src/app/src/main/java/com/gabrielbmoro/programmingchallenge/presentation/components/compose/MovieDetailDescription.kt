package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h4,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .wrapContentWidth(Alignment.Start),
        color = MaterialTheme.colors.onSurface,
    )
}

@Composable
private fun SectionDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .wrapContentWidth(Alignment.Start),
        color = MaterialTheme.colors.onSurface,
    )
}

@Composable
fun MovieDetailDescription(
    title: String,
    overview: String,
    popularity: Float,
    originalLanguage: String
) {
    Column() {
        SectionTitle(title = title)

        SectionTitle(title = stringResource(id = R.string.overview))
        SectionDescription(description = overview)

        SectionTitle(title = stringResource(id = R.string.popularity))
        SectionDescription(description = popularity.toString())

        SectionTitle(title = stringResource(id = R.string.language))
        SectionDescription(description = originalLanguage)
    }
}