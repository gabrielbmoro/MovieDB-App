package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import javax.inject.Inject

class UnFavoriteMovieUseCase @Inject constructor(val repository: MoviesRepository) {
    suspend fun execute(movieTitle: String): Boolean {
        return repository.unFavorite(movieTitle)
    }
}