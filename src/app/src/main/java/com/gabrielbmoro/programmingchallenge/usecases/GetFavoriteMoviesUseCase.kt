package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.usecases.mappers.MovieMapper
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    private val mapper: MovieMapper
) {

    suspend fun execute(): List<Movie> {
        return repository.getFavoriteMovies().map {
            mapper.mapFavorite(it)
        }
    }
}