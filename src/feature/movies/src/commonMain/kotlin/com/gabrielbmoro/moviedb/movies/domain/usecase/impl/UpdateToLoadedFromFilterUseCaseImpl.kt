package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.state.MoviesStateHolder
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesStateFromFilterSelectionUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateToLoadedFromFilterUseCase

class UpdateToLoadedFromFilterUseCaseImpl(
    private val getMoviesStateFromFilterSelection: GetMoviesStateFromFilterSelectionUseCase,
    private val stateHolder: MoviesStateHolder,
): UpdateToLoadedFromFilterUseCase {
    override suspend fun invoke(selectedFilterType: FilterType) {
        stateHolder.run {
            updateState(
                getMoviesStateFromFilterSelection.execute(
                    GetMoviesStateFromFilterSelectionUseCase.Params(
                        filterType = selectedFilterType,
                        menuItems = state.value.menuItems
                    )
                )
            )
        }
    }
}
