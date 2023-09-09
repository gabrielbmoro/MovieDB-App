package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gabrielbmoro.moviedb.feature.movies.R
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.ui.widgets.MoviesCarouselContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

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
}