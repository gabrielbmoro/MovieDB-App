package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie

interface SearchMovieUseCase : UseCase<SearchMovieUseCase.Params, List<Movie>> {
    data class Params(
        val query: String
    )
}

class SearchMovieUseCaseImpl(
    private val repository: MoviesRepository
) : SearchMovieUseCase {

    override suspend fun execute(input: SearchMovieUseCase.Params): List<Movie> {
        return repository.searchMovieBy(
            query = input.query
        )
    }
}
