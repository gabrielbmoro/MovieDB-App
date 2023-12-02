package com.gabrielbmoro.moviedb.details.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface IsFavoriteMovieUseCase {
    suspend operator fun invoke(movieTitle: String): DataOrException<Boolean, Exception>
}

@ViewModelScoped
class IsFavoriteMovieUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : IsFavoriteMovieUseCase {

    override suspend operator fun invoke(movieTitle: String): DataOrException<Boolean, Exception> {
        return repository.checkIsAFavoriteMovie(movieTitle)
    }
}
