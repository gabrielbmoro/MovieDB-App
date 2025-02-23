package com.gabrielbmoro.moviedb.data.repository.datasources.database

import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.dto.FavoriteMovieDTO
import com.gabrielbmoro.moviedb.domain.entities.Movie

fun Movie.toFavoriteMovieDTO(customId: Int? = null): FavoriteMovieDTO {
    return FavoriteMovieDTO(
        id = customId,
        votesAverage = votesAverage,
        title = title,
        language = language,
        overview = overview,
        posterImageUrl = posterImageUrl,
        backdropImageUrl = backdropImageUrl,
        popularity = popularity,
        releaseDate = releaseDate,
        movieId = id,
    )
}

fun FavoriteMovieDTO.toMovie(): Movie {
    return Movie(
        id = movieId,
        releaseDate = releaseDate,
        isFavorite = true,
        language = language,
        popularity = popularity,
        posterImageUrl = posterImageUrl,
        backdropImageUrl = backdropImageUrl,
        overview = overview,
        title = title,
        votesAverage = votesAverage,
    )
}
