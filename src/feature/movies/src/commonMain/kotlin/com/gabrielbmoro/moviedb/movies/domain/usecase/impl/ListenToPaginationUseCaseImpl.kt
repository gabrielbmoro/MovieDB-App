package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.usecase.ListenToPaginationUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateStateBasedOnNextPageUseCase
import com.gabrielbmoro.moviedb.platform.paging.PagingController
import kotlinx.coroutines.flow.collectLatest

class ListenToPaginationUseCaseImpl(
    private val pagingController: PagingController,
    private val updateStateBasedOnNextPage: UpdateStateBasedOnNextPageUseCase,
): ListenToPaginationUseCase {

    override suspend fun invoke() {
        pagingController.currentPage.collectLatest { pageIndex ->
            updateStateBasedOnNextPage(pageIndex)
        }
    }
}
