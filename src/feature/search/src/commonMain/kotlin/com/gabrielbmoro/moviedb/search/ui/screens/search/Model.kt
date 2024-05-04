package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import com.gabrielbmoro.moviedb.domain.entities.Movie

sealed class SearchUserIntent {
    data class SearchBy(val query: TextFieldValue) : SearchUserIntent()

    data object ClearSearchField : SearchUserIntent()

    data class SearchInputFieldChanged(val query: TextFieldValue) : SearchUserIntent()
}

data class SearchUIState(
    val searchQuery: TextFieldValue,
    val results: List<Movie>? = null
)
