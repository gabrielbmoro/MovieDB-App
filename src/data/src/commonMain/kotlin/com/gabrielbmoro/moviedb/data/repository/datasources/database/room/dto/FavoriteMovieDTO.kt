package com.gabrielbmoro.moviedb.data.repository.datasources.database.room.dto

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
)
