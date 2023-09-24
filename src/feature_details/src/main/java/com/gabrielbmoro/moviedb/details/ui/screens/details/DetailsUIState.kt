package com.gabrielbmoro.moviedb.details.ui.screens.details

sealed class DetailsUIState(
    open val movieTitle: String
) {
    data class SuccessData(
        val isFavorite: Boolean,
        val movieVotesAverage: Float,
        val movieLanguage: String,
        val moviePopularity: Float,
        val movieOverview: String,
        val imageUrl: String?,
        val tagLine: String? = null,
        val genres: String? = null,
        val status: String? = null,
        val productionCompanies: String? = null,
        val homepage: String? = null,
        val videoId: String? = null,
        override val movieTitle: String,
    ) : DetailsUIState(movieTitle = movieTitle)

    data class Error(
        val message: String,
        override val movieTitle: String
    ) : DetailsUIState(movieTitle = movieTitle)

    data class Loading(override val movieTitle: String) : DetailsUIState(
        movieTitle = movieTitle
    )

    companion object {
        fun empty() = SuccessData(
            isFavorite = false,
            movieTitle = "",
            moviePopularity = 0f,
            movieLanguage = "",
            movieVotesAverage = 0f,
            movieOverview = "",
            imageUrl = ""
        )

        fun mocked1() = SuccessData(
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
