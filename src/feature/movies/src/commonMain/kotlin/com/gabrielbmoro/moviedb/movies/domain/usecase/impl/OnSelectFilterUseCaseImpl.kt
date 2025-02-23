package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnSelectFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateToLoadedFromFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateToLoadingStateUseCase
import com.gabrielbmoro.moviedb.platform.paging.PagingController

class OnSelectFilterUseCaseImpl(
    private val updateToLoadingState: UpdateToLoadingStateUseCase,
    private val updateToLoadedFromFilter: UpdateToLoadedFromFilterUseCase,
    private val pagingController: PagingController,
): OnSelectFilterUseCase {

    override suspend fun invoke(selectedFilterType: FilterType) {
        updateToLoadingState(selectedFilterType)
        pagingController.resetPaging()
        updateToLoadedFromFilter(selectedFilterType)
    }
}
