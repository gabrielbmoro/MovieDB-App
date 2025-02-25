package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.UseCase
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType

interface GetMoviesFromFilterUseCase : UseCase<GetMoviesFromFilterUseCase.Params, List<Movie>> {
    data class Params(val filter: FilterType, val page: Int)
}
