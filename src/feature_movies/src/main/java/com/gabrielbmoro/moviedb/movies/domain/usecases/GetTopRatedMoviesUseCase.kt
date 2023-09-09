package com.gabrielbmoro.moviedb.movies.domain.usecases

import com.gabrielbmoro.moviedb.repository.MoviesRepository

class GetTopRatedMoviesUseCase(
    private val repository: MoviesRepository,
) {

    operator fun invoke() = repository.getTopRatedMovies()
}