package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import com.gabrielbmoro.moviedb.search.ui.widgets.MovieCardInfo
import kotlinx.collections.immutable.ImmutableList

sealed interface SearchUserIntent {
    data class SearchBy(val query: TextFieldValue) : SearchUserIntent

    data object ClearSearchField : SearchUserIntent
}

data class SearchUIState(
    val searchQuery: TextFieldValue,
    val results: ImmutableList<MovieCardInfo>? = null
)
