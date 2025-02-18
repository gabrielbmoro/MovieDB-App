package com.gabrielbmoro.moviedb.details.ui.screens.details

import kotlinx.collections.immutable.ImmutableList

sealed interface DetailsUserIntent {
    data object HideVideo : DetailsUserIntent

    data object FavoriteMovie : DetailsUserIntent

    data class LoadMovieDetails(
        val movieId: Long
    ): DetailsUserIntent
}

data class DetailsUIState(
    val movieTitle: String,
    val isLoading: Boolean = true,
    val isFavorite: Boolean,
    val movieVotesAverage: Float,
    val movieLanguage: String,
    val moviePopularity: Float,
    val movieOverview: String,
    val imageUrl: String?,
    val tagLine: String? = null,
    val genres: ImmutableList<String>? = null,
    val status: String? = null,
    val productionCompanies: String? = null,
    val homepage: String? = null,
    val videoId: String? = null,
    val showVideo: Boolean = true,
    val errorMessage: String? = null
) {
    companion object {
        fun empty() =
            DetailsUIState(
                isFavorite = false,
                movieTitle = "",
                moviePopularity = 0f,
                movieLanguage = "",
                movieVotesAverage = 0f,
                movieOverview = "",
                imageUrl = ""
            )
    }
}
