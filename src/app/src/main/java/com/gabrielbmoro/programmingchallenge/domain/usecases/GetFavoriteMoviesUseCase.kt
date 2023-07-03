package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFavoriteMoviesUseCase (
    private val repository: MoviesRepository,
) {

    suspend operator fun invoke(): DataOrException<List<Movie>, Exception> {
        return withContext(Dispatchers.IO) {
            repository.getFavoriteMovies()
        }
    }
}