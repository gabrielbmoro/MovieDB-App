package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFilterTypeOrder
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFirstMoviesStateUseCase

class GetFirstMoviesStateUseCaseImpl(
    private val getFilterTypeOrder: GetFilterTypeOrder,
): GetFirstMoviesStateUseCase {
    override operator fun invoke(): MoviesState = MoviesState.Loading(
        getFilterTypeOrder().map {
            FilterMenuItem(
                type = it,
                selected = it == FilterType.NowPlaying,
            )
        }
    )
}
