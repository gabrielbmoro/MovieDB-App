package com.gabrielbmoro.programmingchallenge.usecase

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository

class GetFavoriteMoviesUseCase(val repository: MoviesRepository) {

    suspend fun execute(): List<Movie> {
        return repository.getFavoriteMovies()
    }
}