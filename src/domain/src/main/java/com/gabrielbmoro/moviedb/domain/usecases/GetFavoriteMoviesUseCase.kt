package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface GetFavoriteMoviesUseCase : UseCase<Unit, Flow<List<Movie>>>

class GetFavoriteMoviesUseCaseImpl(
    private val repository: MoviesRepository
) : GetFavoriteMoviesUseCase {
    override suspend fun execute(input: Unit): Flow<List<Movie>> {
        return repository.getFavoriteMovies()
    }
}
