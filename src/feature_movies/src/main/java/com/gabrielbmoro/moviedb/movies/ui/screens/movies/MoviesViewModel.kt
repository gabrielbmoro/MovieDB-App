package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.core.ui.mvi.ViewModelMVI
import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase

class MoviesViewModel(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModelMVI<Intent, MoviesUIState>() {

    private val nowPlayingMoviesPageController = PagingController(
        tag = "nowPlayingPagingController",
        requestMore = { pageIndex ->
            getNowPlayingMoviesUseCase.execute(GetNowPlayingMoviesUseCase.Params(pageIndex))
        },
    )

    private val popularMoviesPageController = PagingController(
        tag = "popularPagingController",
        requestMore = { pageIndex ->
            getPopularMoviesUseCase.execute(GetPopularMoviesUseCase.Params(pageIndex))
        },
    )

    private val topRatedMoviesPageController = PagingController(
        tag = "topRatedPagingController",
        requestMore = { pageIndex ->
            getTopRatedMoviesUseCase.execute(GetTopRatedMoviesUseCase.Params(pageIndex))
        },
    )

    private val upComingMoviesPagingController = PagingController(
        tag = "upcomingPagingController",
        requestMore = { pageIndex ->
            getUpcomingMoviesUseCase.execute(GetUpcomingMoviesUseCase.Params(pageIndex))
        },
    )

    override suspend fun setup(): MoviesUIState {
        return uiState.value.copy(
            nowPlayingMovies = nowPlayingMoviesPageController.onRequestMore(),
            topRatedMovies = topRatedMoviesPageController.onRequestMore(),
            popularMovies = popularMoviesPageController.onRequestMore(),
            upComingMovies = upComingMoviesPagingController.onRequestMore()
        )
    }

    override suspend fun execute(intent: Intent): MoviesUIState {
        return when (intent) {
            is Intent.RequestMoreUpComingMovies -> {
                val movies = upComingMoviesPagingController.onRequestMore()
                uiState.value.copy(
                    upComingMovies = uiState.value.upComingMovies.toMutableList().apply {
                        addAll(movies)
                    }
                )
            }

            is Intent.RequestMoreTopRatedMovies -> {
                val movies = topRatedMoviesPageController.onRequestMore()
                uiState.value.copy(
                    topRatedMovies = uiState.value.topRatedMovies.toMutableList().apply {
                        addAll(movies)
                    }
                )
            }

            is Intent.RequestMorePopularMovies -> {
                val movies = popularMoviesPageController.onRequestMore()
                uiState.value.copy(
                    popularMovies = uiState.value.popularMovies.toMutableList().apply {
                        addAll(movies)
                    }
                )
            }

            is Intent.RequestMoreNowPlayingMovies -> {
                val movies = nowPlayingMoviesPageController.onRequestMore()
                uiState.value.copy(
                    nowPlayingMovies = uiState.value.nowPlayingMovies.toMutableList().apply {
                        addAll(movies)
                    }
                )
            }
        }
    }

    override fun defaultEmptyState() = MoviesUIState(
        nowPlayingMovies = emptyList(),
        popularMovies = emptyList(),
        topRatedMovies = emptyList(),
        upComingMovies = emptyList()
    )
}
