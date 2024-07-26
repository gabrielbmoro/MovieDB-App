package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import ModelViewIntent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.platform.paging.PagingController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory

@Factory
class MoviesViewModel(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel(), ModelViewIntent<Intent, MoviesUIState> {

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

    override suspend fun setup(): MoviesUIState {
        return uiState.value.copy(
            nowPlayingMovies = nowPlayingMoviesPageController.onRequestMore(),
            topRatedMovies = topRatedMoviesPageController.onRequestMore(),
            popularMovies = popularMoviesPageController.onRequestMore(),
            upComingMovies = upComingMoviesPagingController.onRequestMore()
        )
    }

    override suspend fun execute(intent: Intent) {
        when (intent) {
            is Intent.RequestMoreUpComingMovies -> processRequestMoreForUpcomingMoviesIntent()

            is Intent.RequestMoreTopRatedMovies -> processRequestMoreForTopRatedMoviesIntent()

            is Intent.RequestMorePopularMovies -> processRequestMoreForPopularMoviesIntent()

            is Intent.RequestMoreNowPlayingMovies -> processRequestMoreForNowPlayingMoviesIntent()
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

    override fun defaultEmptyState() =
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
