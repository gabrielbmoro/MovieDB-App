package com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows

import com.gabrielbmoro.moviedb.desingsystem.error.ErrorInfo
import com.gabrielbmoro.moviedb.platform.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList

data class TvShowsState(
    val tvShowCardInfos: ImmutableList<TvShowCardInfo>,
    val menuItems: ImmutableList<FilterMenuItem>,
    val selectedFilterMenu: TvShowFilterType,
    val isLoading: Boolean,
    val errorInfo: ErrorInfo?,
) : UiState

data class FilterMenuItem(
    val selected: Boolean,
    val type: TvShowFilterType,
)

enum class TvShowFilterType {
    Popular,
    TopRated,
    OnTheAir,
    AiringToday,
}

data class TvShowCardInfo(
    val tvShowId: Long,
    val tvShowTitle: String,
    val tvShowPosterUrl: String,
)
