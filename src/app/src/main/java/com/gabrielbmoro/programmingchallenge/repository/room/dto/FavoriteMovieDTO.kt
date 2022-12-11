package com.gabrielbmoro.programmingchallenge.repository.room.dto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_movies")
data class FavoriteMovieDTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val votesAverage: Float,
    val title: String,
    val imageUrl: String,
    val overview: String,
    val releaseDate: String,
    val language: String,
    val popularity: Float
)