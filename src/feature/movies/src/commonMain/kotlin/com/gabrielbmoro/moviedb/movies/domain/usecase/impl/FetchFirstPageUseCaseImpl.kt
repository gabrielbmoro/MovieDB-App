package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.repository.MoviesPageRepository
import com.gabrielbmoro.moviedb.movies.domain.usecase.FetchFirstPageUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateStateBasedOnCurrentPageUseCase

class FetchFirstPageUseCaseImpl(
    private val repository: MoviesPageRepository,
    private val updateStateBasedOCurrentPage: UpdateStateBasedOnCurrentPageUseCase,
): FetchFirstPageUseCase {

    override suspend fun invoke() {
        repository.setCurrentPage(1)
        updateStateBasedOCurrentPage()
    }
}
