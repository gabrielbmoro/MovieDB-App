package com.gabrielbmoro.moviedb.feature.tvshowdetails.ui.screens.details

import com.gabrielbmoro.moviedb.platform.viewmodel.UiState
import com.gabrielbmoro.moviedb.platform.viewmodel.UserIntent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface TvShowDetailsUserIntent : UserIntent {
    data class LoadTvShowDetails(val tvShowId: Long) : TvShowDetailsUserIntent
    data object HideVideo : TvShowDetailsUserIntent
}

data class TvShowDetailsUIState(
    val tvShowName: String,
    val isLoading: Boolean = true,
    val votesAverage: Float = 0f,
    val language: String = "",
    val popularity: Float = 0f,
    val overview: String = "",
    val imageUrl: String? = null,
    val tagline: String? = null,
    val genres: ImmutableList<String> = persistentListOf(),
    val status: String? = null,
    val firstAirDate: String? = null,
    val lastAirDate: String? = null,
    val networks: ImmutableList<String> = persistentListOf(),
    val createdBy: ImmutableList<String> = persistentListOf(),
    val productionCompanies: ImmutableList<String> = persistentListOf(),
    val homepage: String? = null,
    val numberOfSeasons: Int = 0,
    val numberOfEpisodes: Int = 0,
    val videoId: String? = null,
    val showVideo: Boolean = true,
    val errorMessage: String? = null,
) : UiState {
    companion object {
        fun empty() = TvShowDetailsUIState(tvShowName = "")
    }
}
