package com.gabrielbmoro.moviedb.movies.domain.model

import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieCardInfo
import kotlinx.collections.immutable.ImmutableList

sealed interface MoviesState {

    val menuItems: List<FilterMenuItem>

    data class Loading(override val menuItems: List<FilterMenuItem>) : MoviesState

    data class Success(
        val movieCardInfos: ImmutableList<MovieCardInfo>,
        override val menuItems: List<FilterMenuItem>,
    ): MoviesState

    data class Error(
        val errorType: MoviesErrorType,
        override val menuItems: List<FilterMenuItem>,
    ): MoviesState
}
