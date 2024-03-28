package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository

interface IsFavoriteMovieUseCase : UseCase<IsFavoriteMovieUseCase.Params, Boolean> {
    data class Params(
        val movieTitle: String,
    )
}

class IsFavoriteMovieUseCaseImpl(
    private val repository: MoviesRepository,
) : IsFavoriteMovieUseCase {
    override suspend fun execute(input: IsFavoriteMovieUseCase.Params): Boolean {
        return repository.checkIsAFavoriteMovie(
            movieTitle = input.movieTitle,
        )
    }
}
