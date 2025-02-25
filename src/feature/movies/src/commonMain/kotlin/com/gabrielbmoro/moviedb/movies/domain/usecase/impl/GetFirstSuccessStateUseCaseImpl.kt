package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFilterTypeOrderUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetFirstSuccessStateUseCase
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieCardInfo
import com.gabrielbmoro.moviedb.platform.Mapper
import kotlinx.collections.immutable.toPersistentList

class GetFirstSuccessStateUseCaseImpl(
    private val movieMapper: Mapper<Movie, MovieCardInfo>,
    private val getFilterTypeOrder: GetFilterTypeOrderUseCase,
): GetFirstSuccessStateUseCase {

    override suspend fun execute(input: GetFirstSuccessStateUseCase.Params): MoviesState.Success = input.run {
        MoviesState.Success(
            menuItems = getFilterTypeOrder().map {
                FilterMenuItem(
                    type = it,
                    selected = it == filterType,
                )
            },
            movieCardInfos = requestedMoreMovies.map(movieMapper::map).toPersistentList(),
        )
    }
}
