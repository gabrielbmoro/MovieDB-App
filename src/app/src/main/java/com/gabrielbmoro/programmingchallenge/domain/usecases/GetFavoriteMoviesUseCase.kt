package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {

    suspend fun execute(): DataOrException<List<Movie>, Exception> {
        return withContext(Dispatchers.IO) {
            repository.getFavoriteMovies()
        }
    }
}