package com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows

import com.gabrielbmoro.moviedb.platform.viewmodel.UserIntent

sealed interface TvShowsIntent : UserIntent {
    data object RequestMoreTvShows : TvShowsIntent

    data object Setup : TvShowsIntent

    data class SelectFilterMenuItem(val menuItem: FilterMenuItem) : TvShowsIntent
}
