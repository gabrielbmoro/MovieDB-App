package com.gabrielbmoro.programmingchallenge.ui.screens.home

import androidx.paging.PagingData
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import kotlinx.coroutines.flow.Flow

data class HomeUIState(
    val favoriteMovies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val selectedMovieType: MovieListType,
    val paginatedMovies: Flow<PagingData<Movie>>
)