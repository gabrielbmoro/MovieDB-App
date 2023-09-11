package com.gabrielbmoro.moviedb.movies.domain.usecases

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {

    operator fun invoke() = repository.getUpcomingMovies()
}