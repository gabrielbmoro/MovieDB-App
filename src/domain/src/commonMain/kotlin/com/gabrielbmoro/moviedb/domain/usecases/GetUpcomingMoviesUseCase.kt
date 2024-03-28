package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie

interface GetUpcomingMoviesUseCase : UseCase<GetUpcomingMoviesUseCase.Params, List<Movie>> {
    data class Params(
        val page: Int,
    )
}

class GetUpcomingMoviesUseCaseImpl(
    private val repository: MoviesRepository,
) : GetUpcomingMoviesUseCase {
    override suspend fun execute(input: GetUpcomingMoviesUseCase.Params): List<Movie> {
        return repository.getUpcomingMovies(
            page = input.page,
        )
    }
}
