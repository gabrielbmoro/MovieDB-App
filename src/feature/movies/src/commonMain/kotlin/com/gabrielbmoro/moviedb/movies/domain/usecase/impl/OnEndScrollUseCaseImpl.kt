package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.repository.MoviesPageRepository
import com.gabrielbmoro.moviedb.movies.domain.usecase.OnRequestMoreMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateStateBasedOnCurrentPageUseCase

class OnEndScrollUseCaseImpl(
    private val pageRepository: MoviesPageRepository,
    private val updateStateBasedOnCurrentPage: UpdateStateBasedOnCurrentPageUseCase,
): OnRequestMoreMoviesUseCase {
    override suspend fun invoke() {
        pageRepository.apply {
            getCurrentPage()?.let { currentPage ->
                setCurrentPage(currentPage.inc())
                updateStateBasedOnCurrentPage()
            }
        }
    }
}
