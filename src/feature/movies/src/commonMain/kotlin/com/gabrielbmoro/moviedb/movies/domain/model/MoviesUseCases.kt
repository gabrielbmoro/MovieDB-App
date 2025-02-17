package com.gabrielbmoro.moviedb.movies.domain.model

import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetDefaultEmptyStateUseCase

data class MoviesUseCases(
    val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getDefaultEmptyState: GetDefaultEmptyStateUseCase,
)
