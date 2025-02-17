package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.movies.ui.widgets.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.ui.widgets.FilterType
import com.gabrielbmoro.moviedb.movies.ui.widgets.MovieCardInfo
import kotlinx.collections.immutable.ImmutableList

data class MoviesUIState(
    val movieCardInfos: ImmutableList<MovieCardInfo>,
    val menuItems: List<FilterMenuItem>,
    val selectedFilterMenu: FilterType,
    val isLoading: Boolean = false,
)

sealed interface Intent {
    data object RequestMoreMovies : Intent

    data object Setup : Intent

    data class SelectFilterMenuItem(val menuItem: FilterMenuItem) : Intent
}
