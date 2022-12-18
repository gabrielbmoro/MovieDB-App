package com.gabrielbmoro.programmingchallenge.ui.screens.home

import androidx.compose.foundation.lazy.LazyListState
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType

data class HomeUIState(
    val movies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val lazyListState: LazyListState = LazyListState(0, 0),
    val selectedMovieListType: MovieListType
)