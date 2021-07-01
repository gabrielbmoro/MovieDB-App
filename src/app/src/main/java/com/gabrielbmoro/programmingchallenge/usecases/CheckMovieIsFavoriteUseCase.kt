package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository

class CheckMovieIsFavoriteUseCase(val repository: MoviesRepository) {
    suspend fun execute(movie: Movie): Boolean {
        return repository.checkIsAFavoriteMovie(movie)
    }
}