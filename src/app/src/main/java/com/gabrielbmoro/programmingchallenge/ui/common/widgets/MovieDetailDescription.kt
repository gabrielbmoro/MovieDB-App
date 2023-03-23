package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.programmingchallenge.R

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .wrapContentWidth(Alignment.Start),
    )
}

@Composable
private fun SectionDescription(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .wrapContentWidth(Alignment.Start),
    )
}

@Composable
fun MovieDetailDescription(
    overview: String,
    popularity: Float,
    originalLanguage: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (overview.isNotEmpty()) {
            SectionTitle(title = stringResource(id = R.string.overview))
            SectionDescription(description = overview)
        }

        if (popularity.toString().isNotEmpty()) {
            SectionTitle(title = stringResource(id = R.string.popularity))
            SectionDescription(description = popularity.toString())
        }

        if (originalLanguage.isNotEmpty()) {
            SectionTitle(title = stringResource(id = R.string.language))
            SectionDescription(description = originalLanguage)
        }
    }
}