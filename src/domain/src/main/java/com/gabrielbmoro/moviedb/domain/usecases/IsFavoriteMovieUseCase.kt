package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.DataOrException

interface IsFavoriteMovieUseCase {
    suspend operator fun invoke(movieTitle: String): DataOrException<Boolean, Exception>
}

class IsFavoriteMovieUseCaseImpl(
    private val repository: MoviesRepository
) : IsFavoriteMovieUseCase {

    override suspend operator fun invoke(movieTitle: String): DataOrException<Boolean, Exception> {
        return repository.checkIsAFavoriteMovie(movieTitle)
    }
}
