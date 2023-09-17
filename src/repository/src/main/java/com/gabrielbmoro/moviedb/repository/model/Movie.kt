package com.gabrielbmoro.moviedb.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Long,
    val votesAverage: Float,
    val title: String,
    val posterImageUrl: String?,
    val backdropImageUrl: String?,
    val overview: String,
    val releaseDate: String,
    var isFavorite: Boolean,
    val language: String,
    val popularity: Float
) : Parcelable {

    companion object {
        fun mockWhiteDragonNotFavorite() = Movie(
            id = 12L,
            2f,
            "Dragão branco",
            "https://dragaobranco.png",
            "https://dragaobranco.png",
            "Movie where Vandame shows how a good Karate fighter fights",
            "2002-02-21",
            language = "pt-br",
            popularity = 2f,
            isFavorite = false
        )

        fun mockWhyDoWeUseItFavorite() = Movie(
            id = 12L,
            isFavorite = true,
            overview = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various v",
            title = "Why do we use it?",
            releaseDate = "02-03-2020",
            posterImageUrl = "https://image/bucket/s3/ga2.png",
            backdropImageUrl = "https://image/bucket/s3/ga1.png",
            popularity = 10f,
            votesAverage = 5f,
            language = "en-US"
        )

        fun mockChuckNorrisVsVandammeMovie() = Movie(
            id = 12L,
            votesAverage = 2f,
            title = "Chuck Norris vs Vandamme",
            backdropImageUrl = "",
            posterImageUrl = "",
            overview = "test",
            releaseDate = "03-02-2023",
            isFavorite = false,
            language = "pt-br",
            popularity = 1f
        )
    }
}
