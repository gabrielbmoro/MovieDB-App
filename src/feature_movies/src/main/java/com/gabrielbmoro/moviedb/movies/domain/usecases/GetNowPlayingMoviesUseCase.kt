package com.gabrielbmoro.moviedb.movies.domain.usecases

import com.gabrielbmoro.moviedb.repository.MoviesRepository

class GetNowPlayingMoviesUseCase(
    private val repository: MoviesRepository,
) {
    operator fun invoke() = repository.getNowPlayingMovies()
}