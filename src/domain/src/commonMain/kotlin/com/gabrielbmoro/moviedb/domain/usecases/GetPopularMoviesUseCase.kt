package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie

interface GetPopularMoviesUseCase : UseCase<GetPopularMoviesUseCase.Params, List<Movie>> {
    data class Params(
        val page: Int
    )
}

class GetPopularMoviesUseCaseImpl(
    private val repository: MoviesRepository
) : GetPopularMoviesUseCase {
    override suspend fun execute(input: GetPopularMoviesUseCase.Params): List<Movie> {
        return repository.getPopularMovies(input.page)
    }
}
