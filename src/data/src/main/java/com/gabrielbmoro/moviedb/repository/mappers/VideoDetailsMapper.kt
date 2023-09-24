package com.gabrielbmoro.moviedb.repository.mappers

import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.MovieDetailResponse
import com.gabrielbmoro.moviedb.repository.model.MovieDetail
import javax.inject.Inject

class VideoDetailsMapper @Inject constructor() {

    fun map(movieDetailResponse: MovieDetailResponse) = MovieDetail(
        adult = movieDetailResponse.adult,
        backdropPath = movieDetailResponse.backdropPath,
        genres = movieDetailResponse.genres.map { it.name }.reduce { acc, s -> "$acc,$s" },
        budget = movieDetailResponse.budget,
        homepage = movieDetailResponse.homepage,
        id = movieDetailResponse.id,
        imdbId = movieDetailResponse.imdbId,
        originalLanguage = movieDetailResponse.originalLanguage,
        originalTitle = movieDetailResponse.originalTitle,
        overview = movieDetailResponse.overview,
        popularity = movieDetailResponse.popularity,
        posterPath = movieDetailResponse.posterPath,
    )
}