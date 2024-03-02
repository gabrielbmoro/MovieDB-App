package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.DataOrException
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface FavoriteMovieUseCase {
    suspend operator fun invoke(
        movie: Movie,
        toFavorite: Boolean
    ): DataOrException<Boolean, Exception>
}

open class FavoriteMovieUseCaseImpl(
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
