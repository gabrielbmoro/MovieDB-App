package com.gabrielbmoro.programmingchallenge.usecases.mappers

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.entities.Page
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.MovieResponse
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import com.gabrielbmoro.programmingchallenge.repository.room.entities.FavoriteMovieDTO

fun PageResponse.toPage(): Page {
    return Page(
        totalPages = totalPages,
        pageNumber = page,
        movies = results?.fromResponseToMovies() ?: emptyList()
    )
}

fun List<MovieResponse>.fromResponseToMovies(): List<Movie> {
    return map { movieResponse -> movieResponse.toMovie() }
}

fun List<FavoriteMovieDTO>.fromDTOsToMovies(): List<Movie> {
    return map { dto ->
        dto.toMovie()
    }
}

fun MovieResponse.toMovie(): Movie {
    return Movie(
        votesAverage = votesAverage ?: 0f,
        title = title ?: "",
        imageUrl = backdropPath ?: "",
        overview = overview ?: "",
        releaseDate = releaseDate ?: "",
        popularity = popularity ?: 0f,
        language = originalLanguage ?: "",
        isFavorite = false
    )
}

fun FavoriteMovieDTO.toMovie(): Movie {
    return Movie(
        releaseDate = releaseDate,
        isFavorite = true,
        language = language,
        popularity = popularity,
        imageUrl = imageUrl,
        overview = overview,
        title = title,
        votesAverage = votesAverage
    )
}

fun Movie.toFavoriteMovie(id: Int? = null): FavoriteMovieDTO {
    return FavoriteMovieDTO(
        id = id,
        votesAverage = votesAverage,
        title = title,
        language = language,
        overview = overview,
        imageUrl = imageUrl,
        popularity = popularity,
        releaseDate = releaseDate
    )
}