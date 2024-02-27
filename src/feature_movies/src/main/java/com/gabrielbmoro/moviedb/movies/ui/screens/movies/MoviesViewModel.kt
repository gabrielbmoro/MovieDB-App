package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.core.ui.mvi.ViewModelMVI
import com.gabrielbmoro.moviedb.feature.movies.R
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.ui.widgets.MoviesCarouselContent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModelMVI<Any, MoviesUIState>() {

    init {
        loadMovies()
    }

    private fun loadMovies() {
        updateState(
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
    }

    override fun defaultEmptyState() = MoviesUIState(
        carousels = emptyList()
    )

    override suspend fun execute(intent: Any): MoviesUIState {
        TODO("Not yet implemented")
    }
}
