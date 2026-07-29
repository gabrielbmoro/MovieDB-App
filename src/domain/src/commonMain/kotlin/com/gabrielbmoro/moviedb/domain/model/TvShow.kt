package com.gabrielbmoro.moviedb.domain.model

data class TvShow(
    val id: Long,
    val name: String,
    val votesAverage: Float,
    val posterImageUrl: String?,
    val backdropImageUrl: String?,
    val overview: String,
    val firstAirDate: String,
    val language: String,
    val popularity: Float,
) {
    companion object {
        fun mockBreakingBad() =
            TvShow(
                id = 1396L,
                votesAverage = 8.9f,
                name = "Breaking Bad",
                posterImageUrl = "https://breakingbad/poster.png",
                backdropImageUrl = "https://breakingbad/backdrop.png",
                overview = "A high school chemistry teacher turned methamphetamine manufacturer.",
                firstAirDate = "2008-01-20",
                language = "en-US",
                popularity = 100f,
            )
    }
}
