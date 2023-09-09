package com.gabrielbmoro.moviedb.wishlist.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFavoriteMoviesUseCase(
    private val repository: MoviesRepository,
) {

    suspend operator fun invoke(): DataOrException<List<Movie>, Exception> {
        return withContext(Dispatchers.IO) {
            repository.getFavoriteMovies()
        }
    }
}