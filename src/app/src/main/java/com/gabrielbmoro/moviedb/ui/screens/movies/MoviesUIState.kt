package com.gabrielbmoro.moviedb.ui.screens.movies

import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class MoviesUIState(
    val topRatedMoviesPagingData: Flow<PagingData<Movie>>,
    val popularMoviesPagingData: Flow<PagingData<Movie>>,
    val upcomingMoviesPagingData: Flow<PagingData<Movie>>,
    val areBarsVisible: Boolean = true,
)