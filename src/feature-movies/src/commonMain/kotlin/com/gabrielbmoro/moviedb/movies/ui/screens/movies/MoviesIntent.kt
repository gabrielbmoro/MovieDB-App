package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.platform.viewmodel.UserIntent

sealed interface MoviesIntent : UserIntent {
    data object RequestMoreMovies : MoviesIntent

    data object Setup : MoviesIntent

    data class SelectFilterMenuItem(val menuItem: FilterMenuItem) : MoviesIntent
}
