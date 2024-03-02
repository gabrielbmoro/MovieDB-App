package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface GetFavoriteMoviesUseCase {
    operator fun invoke(): Flow<List<Movie>>
}

class GetFavoriteMoviesUseCaseImpl(
    private val repository: MoviesRepository
) : GetFavoriteMoviesUseCase {

    override operator fun invoke(): Flow<List<Movie>> = repository.getFavoriteMovies()
}
