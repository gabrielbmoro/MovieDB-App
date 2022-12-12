package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class FavoriteMovieUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {

    suspend operator fun invoke(movie: Movie, toFavorite: Boolean): DataOrException<Boolean,Exception> {
        return withContext(Dispatchers.IO) {
            if(toFavorite)
                repository.doAsFavorite(movie)
            else {
                repository.unFavorite(movie.title)
            }
        }
    }
}