package com.gabrielbmoro.programmingchallenge.domain.usecase

import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository

open class FavoriteMovieUseCase(val repository: MoviesRepository) {

    suspend fun execute(movie: Movie): Boolean {
        return repository.doAsFavorite(movie)
    }
}