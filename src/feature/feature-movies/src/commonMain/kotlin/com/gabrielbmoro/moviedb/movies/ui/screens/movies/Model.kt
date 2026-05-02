package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.desingsystem.error.ErrorInfo
import com.gabrielbmoro.moviedb.platform.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList

data class MoviesState(
    val movieCardInfos: ImmutableList<MovieCardInfo>,
    val menuItems: ImmutableList<FilterMenuItem>,
    val selectedFilterMenu: FilterType,
    val isLoading: Boolean,
    val errorInfo: ErrorInfo?,
) : UiState

data class FilterMenuItem(
    val selected: Boolean,
    val type: FilterType,
)

enum class FilterType {
    NowPlaying,
    TopRated,
    Popular,
    UpComing,
}

data class MovieCardInfo(
    val movieId: Long,
    val movieTitle: String,
    val moviePosterUrl: String,
)
