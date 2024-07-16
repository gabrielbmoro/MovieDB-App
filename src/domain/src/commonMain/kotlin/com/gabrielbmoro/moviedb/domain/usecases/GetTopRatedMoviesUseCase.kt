package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import org.koin.core.annotation.Factory

interface GetTopRatedMoviesUseCase : UseCase<GetTopRatedMoviesUseCase.Params, List<Movie>> {
    data class Params(
        val page: Int
    )
}

@Factory
class GetTopRatedMoviesUseCaseImpl(
    private val repository: MoviesRepository
) : GetTopRatedMoviesUseCase {
    override suspend fun execute(input: GetTopRatedMoviesUseCase.Params): List<Movie> {
        return repository.getTopRatedMovies(
            page = input.page
        )
    }
}
