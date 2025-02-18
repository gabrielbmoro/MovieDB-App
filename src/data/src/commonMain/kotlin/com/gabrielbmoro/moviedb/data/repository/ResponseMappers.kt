package com.gabrielbmoro.moviedb.data.repository

import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.MovieDetailResponse
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.MovieResponse
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.VideoStreamsResponse
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream

private const val BASE_URL = "https://image.tmdb.org/t/p/w"
const val BIG_SIZE_IMAGE_ADDRESS = "${BASE_URL}780"
const val SMALL_SIZE_IMAGE_ADDRESS = "${BASE_URL}300"

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = id,
        votesAverage = vote_average ?: 0f,
        title = title.orEmpty(),
        posterImageUrl = poster_path?.toSmallImageUrl(),
        backdropImageUrl = backdrop_path?.toBigImageUrl(),
        overview = overview.orEmpty(),
        releaseDate = release_date.orEmpty(),
        popularity = popularity ?: 0f,
        language = original_language.orEmpty(),
        isFavorite = false
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
        votesAverage = vote_average ?: 0f,
        title = title.orEmpty(),
        posterImageUrl = poster_path?.toSmallImageUrl(),
        backdropImageUrl = backdrop_path?.toBigImageUrl(),
        overview = overview.orEmpty(),
        releaseDate = release_date.orEmpty(),
        popularity = popularity ?: 0f,
        language = original_language.orEmpty(),
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
            id = it.id
        )
    }
}

private fun String.toSmallImageUrl(): String = SMALL_SIZE_IMAGE_ADDRESS.plus(this)
private fun String.toBigImageUrl(): String = BIG_SIZE_IMAGE_ADDRESS.plus(this)
