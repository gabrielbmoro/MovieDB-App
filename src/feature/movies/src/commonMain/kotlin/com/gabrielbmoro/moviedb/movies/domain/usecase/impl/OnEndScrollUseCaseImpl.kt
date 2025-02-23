package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.usecase.OnEndScrollUseCase
import com.gabrielbmoro.moviedb.platform.paging.PagingController

class OnEndScrollUseCaseImpl(
    private val pagingController: PagingController,
): OnEndScrollUseCase {
    override fun invoke() {
        pagingController.requestNextPage()
    }
}
