package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.core.ui.mvi.ViewModelMVI
import com.gabrielbmoro.moviedb.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.feature.movies.R
import com.gabrielbmoro.moviedb.movies.ui.widgets.MoviesCarouselContent

class MoviesViewModel(
    private val resourcesProvider: ResourcesProvider,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModelMVI<Any, MoviesUIState>() {

    override suspend fun setup(): MoviesUIState {
        val nowPlaying = getNowPlayingMoviesUseCase.execute(GetNowPlayingMoviesUseCase.Params(1))
        val topRated = getTopRatedMoviesUseCase.execute(GetTopRatedMoviesUseCase.Params(1))
        val popular = getPopularMoviesUseCase.execute(GetPopularMoviesUseCase.Params(1))
        val upComing = getUpcomingMoviesUseCase.execute(GetUpcomingMoviesUseCase.Params(1))

        return uiState.value.copy(
            carousels = listOf(
                MoviesCarouselContent(
                    sectionTitle = resourcesProvider.getString(R.string.now_playing),
                    movies = nowPlaying
                ),
                MoviesCarouselContent(
                    sectionTitle = resourcesProvider.getString(R.string.popular),
                    movies = popular
                ),
                MoviesCarouselContent(
                    sectionTitle = resourcesProvider.getString(R.string.top_rated),
                    movies = topRated
                ),
                MoviesCarouselContent(
                    sectionTitle = resourcesProvider.getString(R.string.upcoming),
                    movies = upComing
                )
            )
        )
    }

    override fun defaultEmptyState() = MoviesUIState(
        carousels = emptyList()
    )
}
