package com.gabrielbmoro.moviedb.repository.datasources.room.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val votesAverage: Float,
    val title: String,
    val posterImageUrl: String?,
    val backdropImageUrl: String?,
    val overview: String,
    val releaseDate: String,
    val language: String,
    val popularity: Float,
    val movieId: Long
) {
    companion object {
        fun mockWhyDoWeUseMovie() = FavoriteMovieDTO(
            overview = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various v",
            title = "Why do we use it?",
            releaseDate = "02-03-2020",
            backdropImageUrl = "https://image/bucket/s3/ga.png",
            posterImageUrl = "https://image/bucket/s3/ga.png",
            popularity = 10f,
            votesAverage = 5f,
            language = "en-US",
            movieId = 0L
        )
    }
}
