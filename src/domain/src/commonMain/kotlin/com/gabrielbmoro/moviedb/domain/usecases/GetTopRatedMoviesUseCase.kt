package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie

interface GetTopRatedMoviesUseCase : UseCase<GetTopRatedMoviesUseCase.Params, List<Movie>> {
    data class Params(
        val page: Int,
    )
}

class GetTopRatedMoviesUseCaseImpl(
    private val repository: MoviesRepository,
) : GetTopRatedMoviesUseCase {
    override suspend fun execute(input: GetTopRatedMoviesUseCase.Params): List<Movie> {
        return repository.getTopRatedMovies(
            page = input.page,
        )
    }
}
