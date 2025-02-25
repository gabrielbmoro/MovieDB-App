package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesFromFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesStateFromFilterSelectionUseCase
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieCardInfo
import com.gabrielbmoro.moviedb.platform.Mapper
import kotlinx.collections.immutable.toPersistentList

class GetMoviesStateFromFilterSelectionUseCaseImpl(
    private val getMoviesFromFilter: GetMoviesFromFilterUseCase,
    private val movieMapper: Mapper<Movie, MovieCardInfo>,
) : GetMoviesStateFromFilterSelectionUseCase {

    override suspend fun execute(
        input: GetMoviesStateFromFilterSelectionUseCase.Params,
    ): MoviesState = input.run {
        MoviesState.Success(
            movieCardInfos = getMoviesFromFilter.execute(
                GetMoviesFromFilterUseCase.Params(
                    filter = filterType,
                    page = FIRST_PAGE_VALUE,
                ),
            ).map(movieMapper::map).toPersistentList(),
            menuItems = menuItems,
        )
    }

    companion object {
        private const val FIRST_PAGE_VALUE = 1
    }
}
