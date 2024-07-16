package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import org.koin.core.annotation.Factory

interface GetFavoriteMoviesUseCase : UseCase<Unit, List<Movie>>

@Factory
class GetFavoriteMoviesUseCaseImpl(
    private val repository: MoviesRepository
) : GetFavoriteMoviesUseCase {
    override suspend fun execute(input: Unit): List<Movie> {
        return repository.getFavoriteMovies()
    }
}
