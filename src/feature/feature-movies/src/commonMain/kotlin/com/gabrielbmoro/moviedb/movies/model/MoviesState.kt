package com.gabrielbmoro.moviedb.movies.model

import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieCardInfo
import kotlinx.collections.immutable.ImmutableList

data class MoviesState(
    val movieCardInfos: ImmutableList<MovieCardInfo>,
    val menuItems: List<FilterMenuItem>,
    val selectedFilterMenu: FilterType,
    val isLoading: Boolean,
)
