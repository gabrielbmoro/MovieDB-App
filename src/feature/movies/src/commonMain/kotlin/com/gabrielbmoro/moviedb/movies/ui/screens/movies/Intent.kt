package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem

sealed interface Intent {
    data object RequestMoreMovies : Intent

    data object Setup : Intent

    data class SelectFilterMenuItem(val menuItem: FilterMenuItem) : Intent
}
