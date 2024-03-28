package com.gabrielbmoro.moviedb.details.ui.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SectionBody(
    body: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = body,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
    )
}
