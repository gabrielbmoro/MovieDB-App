package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

interface GetUpcomingMoviesUseCase : UseCase<GetUpcomingMoviesUseCase.Params, List<Movie>> {
    data class Params(
        val page: Int
    )
}

@Factory
class GetUpcomingMoviesUseCaseImpl(
    @Provided private val repository: MoviesRepository
) : GetUpcomingMoviesUseCase {
    override suspend fun execute(input: GetUpcomingMoviesUseCase.Params): List<Movie> {
        return repository.getUpcomingMovies(
            page = input.page
        )
    }
}
