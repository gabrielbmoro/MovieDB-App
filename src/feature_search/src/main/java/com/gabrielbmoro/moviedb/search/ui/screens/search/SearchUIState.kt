package com.gabrielbmoro.moviedb.search.ui.screens.search

import com.gabrielbmoro.moviedb.repository.model.Movie

data class SearchUIState(
    val searchQuery: String,
    val results: List<Movie>? = null
)
