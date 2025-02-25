package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.repository.MoviesPageRepository
import com.gabrielbmoro.moviedb.movies.domain.state.MoviesStateHolder
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesStateFromPaginationUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateStateBasedOnCurrentPageUseCase

class UpdateStateBasedOnCurrentPageUseCaseImpl(
    private val stateHolder: MoviesStateHolder,
    private val repository: MoviesPageRepository,
    private val getMoviesStateFromPagination: GetMoviesStateFromPaginationUseCase,
): UpdateStateBasedOnCurrentPageUseCase {

    override suspend fun invoke() {
        val currentState = stateHolder.state.value
        val filterType = currentState.menuItems.first { it.selected }.type
        stateHolder.updateState(
            getMoviesStateFromPagination.execute(
                GetMoviesStateFromPaginationUseCase.Params(
                    currentState = currentState,
                    filterType = filterType,
                    pageIndex = repository.getCurrentPage() ?: return,
                ),
            ),
        )
    }
}
