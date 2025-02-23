package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import com.gabrielbmoro.moviedb.movies.domain.repository.MoviesPageRepository
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnSelectFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateToLoadedFromFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateToLoadingStateUseCase

class OnSelectFilterUseCaseImpl(
    private val updateToLoadingState: UpdateToLoadingStateUseCase,
    private val pageRepository: MoviesPageRepository,
    private val updateToLoadedFromFilter: UpdateToLoadedFromFilterUseCase,
): OnSelectFilterUseCase {

    override suspend fun invoke(selectedFilterType: FilterType) {
        updateToLoadingState(selectedFilterType)
        pageRepository.setCurrentPage(1)
        updateToLoadedFromFilter(selectedFilterType)
    }
}
