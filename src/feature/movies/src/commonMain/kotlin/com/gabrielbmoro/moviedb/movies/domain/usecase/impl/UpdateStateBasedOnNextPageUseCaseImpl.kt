package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.state.MoviesStateHolder
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesStateFromPaginationUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateStateBasedOnNextPageUseCase

class UpdateStateBasedOnNextPageUseCaseImpl(
    private val stateHolder: MoviesStateHolder,
    private val getMoviesStateFromPaginationUseCase: GetMoviesStateFromPaginationUseCase,
): UpdateStateBasedOnNextPageUseCase {

    override suspend fun invoke(page: Int) {
        val currentState = stateHolder.state.value
        val filterType = currentState.menuItems.first { it.selected }.type
        stateHolder.updateState(
            getMoviesStateFromPaginationUseCase.execute(
                GetMoviesStateFromPaginationUseCase.Params(
                    currentState = currentState,
                    filterType = filterType,
                    pageIndex = page,
                ),
            ),
        )
    }
}
