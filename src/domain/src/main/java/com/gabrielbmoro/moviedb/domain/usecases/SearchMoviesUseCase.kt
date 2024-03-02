package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.flow.first

interface SearchMovieUseCase {
    suspend operator fun invoke(query: String): List<Movie>
}

class SearchMovieUseCaseImpl(
    private val repository: MoviesRepository
) : SearchMovieUseCase {
    override suspend operator fun invoke(query: String): List<Movie> {
        return repository.searchMovieBy(query).first()
    }
}
