package com.gabrielbmoro.moviedb.details.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.Movie
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface FavoriteMovieUseCase {
    suspend operator fun invoke(
        movie: Movie,
        toFavorite: Boolean
    ): DataOrException<Boolean, Exception>
}

@ViewModelScoped
open class FavoriteMovieUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : FavoriteMovieUseCase {

    override suspend operator fun invoke(
        movie: Movie,
        toFavorite: Boolean
    ): DataOrException<Boolean, Exception> {
        return withContext(Dispatchers.IO) {
            when {
                (toFavorite && repository.checkIsAFavoriteMovie(movie.title).data == false) -> {
                    repository.doAsFavorite(movie)
                }

                (!toFavorite && repository.checkIsAFavoriteMovie(movie.title).data == true) -> {
                    repository.unFavorite(movie.title)
                }

                else -> DataOrException(
                    exception = IllegalStateException("Not possible to perform this operation")
                )
            }
        }
    }
}
