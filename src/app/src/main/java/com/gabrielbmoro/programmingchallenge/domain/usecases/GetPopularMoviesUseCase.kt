package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository

class GetPopularMoviesUseCase constructor(
    private val repository: MoviesRepository,
) {
    operator fun invoke() = repository.getPopularMovies()
}