package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import javax.inject.Inject

class UnFavoriteMovieUseCase @Inject constructor(val repository: MoviesRepository) {
    suspend fun execute(movie: Movie): Boolean {
        return repository.unFavorite(movie)
    }
}