package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

interface GetPopularMoviesUseCase : UseCase<GetPopularMoviesUseCase.Params, List<Movie>> {
    data class Params(
        val page: Int,
    )
}

@Factory
class GetPopularMoviesUseCaseImpl(
    @Provided private val repository: MoviesRepository,
) : GetPopularMoviesUseCase {
    override suspend fun execute(input: GetPopularMoviesUseCase.Params): List<Movie> {
        return repository.getPopularMovies(input.page)
    }
}
