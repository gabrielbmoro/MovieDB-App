package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository

interface IsFavoriteMovieUseCase {
    suspend operator fun invoke(movieTitle: String): Boolean
}

class IsFavoriteMovieUseCaseImpl(
    private val repository: MoviesRepository
) : IsFavoriteMovieUseCase {

    override suspend operator fun invoke(movieTitle: String): Boolean {
        return repository.checkIsAFavoriteMovie(movieTitle)
    }
}
