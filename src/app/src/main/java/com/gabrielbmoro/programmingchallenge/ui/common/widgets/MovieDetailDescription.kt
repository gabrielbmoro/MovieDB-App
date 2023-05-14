package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
private fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}

@Composable
fun MovieDetailDescription(
    @StringRes titleRes: Int,
    content: @Composable ()->Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SectionTitle(title = stringResource(id = titleRes))
        content()
    }
}