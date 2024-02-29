package com.gabrielbmoro.moviedb.search.domain

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.Movie
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
