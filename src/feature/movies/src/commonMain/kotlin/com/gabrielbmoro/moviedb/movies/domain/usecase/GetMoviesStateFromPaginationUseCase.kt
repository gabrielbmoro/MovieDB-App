package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.domain.usecases.UseCase
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState

interface GetMoviesStateFromPaginationUseCase :
    UseCase<GetMoviesStateFromPaginationUseCase.Params, MoviesState> {

    data class Params(
        val pageIndex: Int,
        val filterType: FilterType,
        val currentState: MoviesState,
    )
}
