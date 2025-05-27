package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.movies.model.FilterMenuItem

sealed interface MoviesIntent {
    data object RequestMoreMovies : MoviesIntent

    data object Setup : MoviesIntent

    data class SelectFilterMenuItem(val menuItem: FilterMenuItem) : MoviesIntent
}
