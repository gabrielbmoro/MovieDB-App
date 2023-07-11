package com.gabrielbmoro.moviedb.ui.screens.details

data class DetailsUIState(
    val isFavorite: Boolean,
    val movieTitle: String,
    val isLoading: Boolean = false,
    val movieVotesAverage: Float,
    val movieLanguage: String,
    val moviePopularity: Float,
    val movieOverview: String,
    val imageUrl: String?,
    val videoId: String? = null,
) {
    companion object {
        fun empty() = DetailsUIState(
            isFavorite = false,
            movieTitle = "",
            moviePopularity = 0f,
            movieLanguage = "",
            movieVotesAverage = 0f,
            movieOverview = "",
            imageUrl = ""
        )

        fun mocked1() = DetailsUIState(
            isFavorite = true,
            movieTitle = "Dragão branco",
            moviePopularity = 2f,
            movieLanguage = "pt-BR",
            movieVotesAverage = 5f,
            movieOverview = "Um filme que conta a história de um lutador de karatê",
            imageUrl = ""
        )
    }
}