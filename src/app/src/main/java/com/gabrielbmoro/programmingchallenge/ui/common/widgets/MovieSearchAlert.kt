package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gabrielbmoro.programmingchallenge.R

enum class SearchType(@StringRes val descriptionRes: Int) {
    TOP_RATED(R.string.top_rated_movies_title),
    POPULAR(R.string.popular_movies_title),
}

@Composable
private fun SearchOption(
    title: String,
    isChecked: Boolean,
    onClick: (() -> Unit),
    modifier: Modifier = Modifier
) {
    Row(modifier.clickable(onClick = onClick)) {
        RadioButton(
            modifier = Modifier.align(CenterVertically),
            selected = isChecked,
            onClick = onClick
        )
        Text(
            modifier = Modifier.align(CenterVertically),
            text = title,
        )
    }
}

@Composable
fun MovieSearchAlert(
    onDismissAlert: (() -> Unit),
    searchType: SearchType,
    onSearch: ((SearchType) -> Unit),
    modifier: Modifier = Modifier,
) {

    var currentSearchSelected by remember {
        mutableStateOf(searchType)
    }

    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = {
            onDismissAlert()
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismissAlert()
                    onSearch(currentSearchSelected)
                }
            ) {
                Text(text = stringResource(id = R.string.search))
            }
        },
        text = {
            Column {
                SearchOption(
                    isChecked = currentSearchSelected == SearchType.TOP_RATED,
                    title = stringResource(id = SearchType.TOP_RATED.descriptionRes),
                    onClick = {
                        currentSearchSelected = SearchType.TOP_RATED
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                SearchOption(
                    isChecked = currentSearchSelected == SearchType.POPULAR,
                    title = stringResource(id = SearchType.POPULAR.descriptionRes),
                    onClick = {
                        currentSearchSelected = SearchType.POPULAR
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        title = {
            Text(text = stringResource(id = R.string.filter_by))
        }
    )
}