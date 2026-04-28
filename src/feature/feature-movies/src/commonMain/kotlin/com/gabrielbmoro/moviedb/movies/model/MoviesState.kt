package com.gabrielbmoro.moviedb.movies.model

import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieCardInfo
import com.gabrielbmoro.moviedb.platform.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList

data class MoviesState(
    val movieCardInfos: ImmutableList<MovieCardInfo>,
    val menuItems: ImmutableList<FilterMenuItem>,
    val selectedFilterMenu: FilterType,
    val isLoading: Boolean,
    val showError: Boolean,
) : UiState
