package com.gabrielbmoro.moviedb.movies.domain.interactor

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetDefaultEmptyStateUseCase

class MoviesInteractor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    val getDefaultEmptyState: GetDefaultEmptyStateUseCase,
) {
    suspend fun getUpcomingMovies(page: Int): List<Movie> = getUpcomingMoviesUseCase.execute(
        GetUpcomingMoviesUseCase.Params(page)
    )

    suspend fun getPopularMovies(page: Int): List<Movie> = getPopularMoviesUseCase.execute(
        GetPopularMoviesUseCase.Params(page)
    )

    suspend fun getTopRatedMovies(page: Int): List<Movie> = getTopRatedMoviesUseCase.execute(
        GetTopRatedMoviesUseCase.Params(page)
    )

    suspend fun getNowPlayingMovies(page: Int): List<Movie> = getNowPlayingMoviesUseCase.execute(
        GetNowPlayingMoviesUseCase.Params(page)
    )
}
