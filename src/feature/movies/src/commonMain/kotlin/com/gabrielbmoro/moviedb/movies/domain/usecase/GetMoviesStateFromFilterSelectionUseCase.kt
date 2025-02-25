package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.domain.usecases.UseCase
import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState

interface GetMoviesStateFromFilterSelectionUseCase :
    UseCase<GetMoviesStateFromFilterSelectionUseCase.Params, MoviesState> {
    data class Params(
        val filterType: FilterType,
        val menuItems: List<FilterMenuItem>,
    )
}
