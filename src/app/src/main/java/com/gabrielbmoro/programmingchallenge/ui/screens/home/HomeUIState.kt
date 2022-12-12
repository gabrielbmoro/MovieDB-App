package com.gabrielbmoro.programmingchallenge.ui.screens.home

import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType

data class HomeUIState(
    val movies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val selectedMovieListType: MovieListType
)