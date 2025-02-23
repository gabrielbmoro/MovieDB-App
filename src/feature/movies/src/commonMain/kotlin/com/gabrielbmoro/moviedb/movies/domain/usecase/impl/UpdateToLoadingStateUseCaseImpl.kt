package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import com.gabrielbmoro.moviedb.movies.domain.state.MoviesStateHolder
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateSelectedMenuItemUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateToLoadingStateUseCase

class UpdateToLoadingStateUseCaseImpl(
    private val stateHolder: MoviesStateHolder,
    private val updateSelectedMenuItemUseCase: UpdateSelectedMenuItemUseCase,
): UpdateToLoadingStateUseCase {
    override suspend fun invoke(selectedFilterType: FilterType) {
        stateHolder.run {
            updateState(
                MoviesState.Loading(
                    menuItems = updateSelectedMenuItemUseCase.execute(
                        UpdateSelectedMenuItemUseCase.Params(
                            items = state.value.menuItems,
                            filterType = selectedFilterType,
                        ),
                    ),
                ),
            )
        }
    }
}
