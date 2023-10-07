package com.gabrielbmoro.moviedb.search.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.core.ui.theme.MovieDBAppTheme
import com.gabrielbmoro.moviedb.core.ui.theme.ThemePreviews
import com.gabrielbmoro.moviedb.feature.search.R

@Composable
fun SearchInputText(
    currentValue: String,
    onValueChanged: ((String) -> Unit),
    onClearText: (() -> Unit),
    modifier: Modifier = Modifier
) {
    val trailingIcon: @Composable (() -> Unit)? = if (currentValue.isNotEmpty()) {
        {
            IconButton(
                onClick = onClearText,
                modifier = Modifier
                    .size(48.dp)
            ) {
                Image(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = android.R.string.cancel)
                )
            }
        }
    } else null

    TextField(
        value = currentValue,
        textStyle = MaterialTheme.typography.labelMedium,
        onValueChange = onValueChanged,
        placeholder = {
            Text(stringResource(id = R.string.search_movie_placeholder))
        },
        trailingIcon = trailingIcon,
        modifier = modifier
    )
}

@ThemePreviews
@Composable
fun SearchInputTextPreview() {
    MovieDBAppTheme {
        SearchInputText(currentValue = "teste", onValueChanged = {}, onClearText = {})
    }
}