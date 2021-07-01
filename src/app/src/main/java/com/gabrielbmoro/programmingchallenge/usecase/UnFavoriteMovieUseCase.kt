package com.gabrielbmoro.programmingchallenge.usecase

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository

class UnFavoriteMovieUseCase(val repository: MoviesRepository) {
    suspend fun execute(movie: Movie): Boolean {
        return repository.unFavorite(movie)
    }
}