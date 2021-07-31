package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.usecases.mappers.fromDTOsToMovies
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(val repository: MoviesRepository) {

    suspend fun execute(): List<Movie> {
        return repository.getFavoriteMovies().fromDTOsToMovies()
    }
}