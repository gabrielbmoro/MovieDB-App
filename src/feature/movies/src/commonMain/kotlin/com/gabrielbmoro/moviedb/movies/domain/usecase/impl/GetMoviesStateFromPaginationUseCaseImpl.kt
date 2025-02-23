package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.model.MoviesErrorType
import com.gabrielbmoro.moviedb.movies.domain.model.MoviesState
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesFromFilterUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetMoviesStateFromPaginationUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecase.GetUpdatedMoviesSuccessStateUseCase

class GetMoviesStateFromPaginationUseCaseImpl(
    private val getMoviesFromFilter: GetMoviesFromFilterUseCase,
    private val getUpdatedMoviesSuccessState: GetUpdatedMoviesSuccessStateUseCase
) : GetMoviesStateFromPaginationUseCase {

    override suspend fun execute(
        input: GetMoviesStateFromPaginationUseCase.Params,
    ): MoviesState = input.run {
        runCatching {
            getMoviesFromFilter.execute(
                GetMoviesFromFilterUseCase.Params(
                    filter = filterType,
                    page = pageIndex,
                ),
            )
        }.getOrNull()?.let { requestedMoreMovies ->
            getUpdatedMoviesSuccessState.execute(
                GetUpdatedMoviesSuccessStateUseCase.Params(
                    currentState = currentState,
                    filterType = filterType,
                    requestedMoreMovies = requestedMoreMovies,
                )
            )
        } ?: MoviesState.Error(
            errorType = MoviesErrorType.UNKNOWN,
            menuItems = currentState.menuItems,
        )
    }
}
