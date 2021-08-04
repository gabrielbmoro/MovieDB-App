package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.usecases.mappers.FavoriteMovieMapper
import javax.inject.Inject

open class FavoriteMovieUseCase @Inject constructor(
    private val repository: MoviesRepository,
    private val mapper: FavoriteMovieMapper
) {

    suspend fun execute(movie: Movie): Boolean {
        return repository.doAsFavorite(
            mapper.map(
                movie = movie
            )
        )
    }
}