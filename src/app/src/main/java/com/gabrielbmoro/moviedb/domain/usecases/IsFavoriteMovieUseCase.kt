package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.repository.MoviesRepository

class IsFavoriteMovieUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieTitle: String): DataOrException<Boolean, Exception> {
        return repository.checkIsAFavoriteMovie(movieTitle)
    }
}