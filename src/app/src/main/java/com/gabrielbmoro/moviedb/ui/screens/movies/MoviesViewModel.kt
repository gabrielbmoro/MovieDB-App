package com.gabrielbmoro.moviedb.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gabrielbmoro.moviedb.R
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.ui.common.widgets.MoviesCarouselContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MoviesViewModel(
    resourcesProvider: ResourcesProvider,
    getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        MoviesUIState(
            carousels = listOf(
                MoviesCarouselContent(
                    sectionTitle = resourcesProvider.getString(R.string.now_playing),
                    movies = getNowPlayingMoviesUseCase().cachedIn(viewModelScope)
                ),
                MoviesCarouselContent(
                    sectionTitle = resourcesProvider.getString(R.string.popular),
                    movies = getPopularMoviesUseCase().cachedIn(viewModelScope)
                ),
                MoviesCarouselContent(
                    sectionTitle = resourcesProvider.getString(R.string.top_rated),
                    movies = getTopRatedMoviesUseCase().cachedIn(viewModelScope)
                ),
                MoviesCarouselContent(
                    sectionTitle = resourcesProvider.getString(R.string.upcoming),
                    movies = getUpcomingMoviesUseCase().cachedIn(viewModelScope)
                )
            )
        )
    )

    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    fun showBars(show: Boolean) {
        _uiState.update {
            it.copy(areBarsVisible = show)
        }
    }
}