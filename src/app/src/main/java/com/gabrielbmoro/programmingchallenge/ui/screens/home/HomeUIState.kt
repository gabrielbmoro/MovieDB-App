package com.gabrielbmoro.programmingchallenge.ui.screens.home

import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType

sealed class HomeUIState {
    data class FavoriteTabUIState(
        val favoriteMovies: List<Movie>? = null,
        val isLoading: Boolean = false
    ) : HomeUIState()

    data class MoviesTabUIState(
        val selectedMovieType: MovieListType
    ) : HomeUIState()
}