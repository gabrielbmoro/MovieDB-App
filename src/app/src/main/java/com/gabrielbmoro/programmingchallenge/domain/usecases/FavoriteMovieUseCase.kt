package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class FavoriteMovieUseCase constructor(
    private val repository: MoviesRepository,
) {

    suspend operator fun invoke(
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