package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import com.gabrielbmoro.moviedb.repository.model.Movie

data class SearchUIState(
    val searchQuery: TextFieldValue,
    val results: List<Movie>? = null
)
