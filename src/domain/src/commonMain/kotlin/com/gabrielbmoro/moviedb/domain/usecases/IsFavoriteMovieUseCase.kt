package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

interface IsFavoriteMovieUseCase : UseCase<IsFavoriteMovieUseCase.Params, Boolean> {
    data class Params(
        val movieTitle: String,
    )
}

@Factory
class IsFavoriteMovieUseCaseImpl(
    @Provided private val repository: MoviesRepository,
) : IsFavoriteMovieUseCase {
    override suspend fun execute(input: IsFavoriteMovieUseCase.Params): Boolean {
        return repository.checkIsAFavoriteMovie(
            movieTitle = input.movieTitle,
        )
    }
}
