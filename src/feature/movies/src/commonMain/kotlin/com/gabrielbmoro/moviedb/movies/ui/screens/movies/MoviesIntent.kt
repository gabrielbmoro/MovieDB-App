package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType

sealed interface MoviesIntent {
    data object OnEndScroll : MoviesIntent
    data class SelectFilterMenuItem(val filterType: FilterType) : MoviesIntent
}
