package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.platform.paging.PagingController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    private val nowPlayingMoviesPageController =
        PagingController(
            requestMore = { pageIndex ->
                getNowPlayingMoviesUseCase.execute(GetNowPlayingMoviesUseCase.Params(pageIndex))
            }
        )

    private val popularMoviesPageController =
        PagingController(
            requestMore = { pageIndex ->
                getPopularMoviesUseCase.execute(GetPopularMoviesUseCase.Params(pageIndex))
            }
        )

    private val topRatedMoviesPageController =
        PagingController(
            requestMore = { pageIndex ->
                getTopRatedMoviesUseCase.execute(GetTopRatedMoviesUseCase.Params(pageIndex))
            }
        )

    private val upComingMoviesPagingController =
        PagingController(
            requestMore = { pageIndex ->
                getUpcomingMoviesUseCase.execute(GetUpcomingMoviesUseCase.Params(pageIndex))
            }
        )

    init {
        viewModelScope.launch { setup() }
    }

    private suspend fun setup() {
        _uiState.update {
            it.copy(
                nowPlayingMovies = nowPlayingMoviesPageController.onRequestMore(),
                topRatedMovies = topRatedMoviesPageController.onRequestMore(),
                popularMovies = popularMoviesPageController.onRequestMore(),
                upComingMovies = upComingMoviesPagingController.onRequestMore()
            )
        }
    }

    fun execute(intent: Intent) {
        when (intent) {
            is Intent.RequestMoreUpComingMovies -> {
                viewModelScope.launch(ioDispatcher) {
                    processRequestMoreForUpcomingMoviesIntent()
                }
            }

            is Intent.RequestMoreTopRatedMovies -> {
                viewModelScope.launch(ioDispatcher) {
                    processRequestMoreForTopRatedMoviesIntent()
                }
            }

            is Intent.RequestMorePopularMovies -> viewModelScope.launch(ioDispatcher) {
                processRequestMoreForPopularMoviesIntent()
            }

            is Intent.RequestMoreNowPlayingMovies -> viewModelScope.launch(ioDispatcher) {
                processRequestMoreForNowPlayingMoviesIntent()
            }
        }
    }

    private suspend fun processRequestMoreForUpcomingMoviesIntent() {
        val movies = upComingMoviesPagingController.onRequestMore()
        _uiState.update {
            it.copy(
                upComingMovies =
                uiState.value.upComingMovies.addAllDistinctly(
                    movies
                )
            )
        }
    }

    private suspend fun processRequestMoreForTopRatedMoviesIntent() {
        val movies = topRatedMoviesPageController.onRequestMore()
        _uiState.update {
            it.copy(
                topRatedMovies =
                uiState.value.topRatedMovies.addAllDistinctly(
                    movies
                )
            )
        }
    }

    private suspend fun processRequestMoreForPopularMoviesIntent() {
        val movies = popularMoviesPageController.onRequestMore()
        _uiState.update {
            it.copy(
                popularMovies =
                uiState.value.popularMovies.addAllDistinctly(
                    movies
                )
            )
        }
    }

    private suspend fun processRequestMoreForNowPlayingMoviesIntent() {
        val movies = nowPlayingMoviesPageController.onRequestMore()
        _uiState.update {
            it.copy(
                nowPlayingMovies =
                uiState.value.nowPlayingMovies.addAllDistinctly(
                    movies
                )
            )
        }
    }

    private fun defaultEmptyState() =
        MoviesUIState(
            nowPlayingMovies = emptyList(),
            popularMovies = emptyList(),
            topRatedMovies = emptyList(),
            upComingMovies = emptyList()
        )

    private fun List<Movie>.addAllDistinctly(newMovies: List<Movie>): List<Movie> {
        return toMutableList().apply {
            addAll(newMovies)
        }.distinctBy { it.id }
    }
}
