package com.gabrielbmoro.moviedb.movies.domain.interactor

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetDefaultEmptyStateUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.getmovies.GetMoviesFromFilterUseCase

class MoviesInteractor(
    private val getMoviesFromFilter: GetMoviesFromFilterUseCase,
    val getDefaultEmptyState: GetDefaultEmptyStateUseCase,
) {
    suspend fun getMoviesFromFilter(filter: FilterType, page: Int): List<Movie> =
        getMoviesFromFilter.execute(GetMoviesFromFilterUseCase.Params(filter, page))
}
