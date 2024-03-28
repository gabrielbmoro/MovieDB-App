package com.gabrielbmoro.moviedb.data.repository

import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.MovieDetailResponse
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.MovieResponse
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.VideoStreamsResponse
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream

const val BIG_SIZE_IMAGE_ADDRESS = "https://image.tmdb.org/t/p/w780"
const val SMALL_SIZE_IMAGE_ADDRESS = "https://image.tmdb.org/t/p/w300"

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = id,
        votesAverage = vote_average ?: 0f,
        title = title ?: "",
        posterImageUrl = poster_path?.let { SMALL_SIZE_IMAGE_ADDRESS.plus(it) },
        backdropImageUrl =
            backdrop_path?.let {
                BIG_SIZE_IMAGE_ADDRESS.plus(
                    backdrop_path,
                )
            },
        overview = overview ?: "",
        releaseDate = release_date ?: "",
        popularity = popularity ?: 0f,
        language = original_language ?: "",
        isFavorite = false,
    )
}

fun MovieDetailResponse.toMovieDetail(): MovieDetail {
    return MovieDetail(
        adult = adult,
        genres = genres.map { it.name },
        budget = budget,
        homepage = homepage,
        imdbId = imdb_id,
        status = status,
        tagline = tagline,
        productionCompanies = production_companies.map { it.name },
    )
}

fun VideoStreamsResponse.toVideoStreams(): List<VideoStream> {
    return results.map {
        VideoStream(
            key = it.key,
            site = it.site,
            size = it.size,
            type = it.type,
            official = it.official,
            name = it.name,
            id = it.id,
        )
    }
}
