package com.gabrielbmoro.moviedb.details.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import javax.inject.Inject

class IsFavoriteMovieUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieTitle: String): DataOrException<Boolean, Exception> {
        return repository.checkIsAFavoriteMovie(movieTitle)
    }
}