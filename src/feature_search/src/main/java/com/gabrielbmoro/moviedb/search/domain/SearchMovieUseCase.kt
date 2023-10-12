package com.gabrielbmoro.moviedb.search.domain

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(query: String) = repository.searchMovieBy(query)
}
