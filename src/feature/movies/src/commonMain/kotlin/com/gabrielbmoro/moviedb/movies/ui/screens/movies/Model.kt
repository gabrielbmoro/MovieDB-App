package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.ui.widgets.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.ui.widgets.FilterType

data class MoviesUIState(
    val movies: List<Movie>,
    val menuItems: List<FilterMenuItem>,
    val selectedFilterMenu: FilterType,
    val isLoading: Boolean = false,
)

sealed class Intent {
    data object RequestMoreMovies : Intent()

    data object Setup : Intent()

    data class SelectFilterMenuItem(val menuItem: FilterMenuItem) : Intent()
}
