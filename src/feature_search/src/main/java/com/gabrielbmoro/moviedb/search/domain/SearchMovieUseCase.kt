package com.gabrielbmoro.moviedb.search.domain

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.repository.model.Movie
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchMovieUseCase {
    operator fun invoke(query: String): Flow<List<Movie>>
}

@ViewModelScoped
class SearchMovieUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : SearchMovieUseCase {
    override operator fun invoke(query: String) = repository.searchMovieBy(query)
}
