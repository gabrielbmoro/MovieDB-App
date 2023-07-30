package com.gabrielbmoro.moviedb.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MoviesViewModel(
    getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MoviesUIState(
            upcomingMoviesPagingData = getUpcomingMoviesUseCase()
                .cachedIn(viewModelScope),
            topRatedMoviesPagingData = getTopRatedMoviesUseCase()
                .cachedIn(viewModelScope),
            popularMoviesPagingData = getPopularMoviesUseCase()
                .cachedIn(viewModelScope)
        )
    )

    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    fun updateScrollPosition(y: Float) {
        _uiState.update {
            it.copy(areBarsVisible = y > 0)
        }
    }
}