package com.gabrielbmoro.moviedb.repository.mappers

import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.MovieDetailResponse

class VideoDetailsMapper {

    fun map(movieDetailResponse: MovieDetailResponse) = MovieDetail(
        adult = movieDetailResponse.adult,
        genres = movieDetailResponse.genres.map { it.name },
        budget = movieDetailResponse.budget,
        homepage = movieDetailResponse.homepage,
        imdbId = movieDetailResponse.imdbId,
        status = movieDetailResponse.status,
        tagline = movieDetailResponse.tagline,
        productionCompanies = movieDetailResponse.productionCompanies.map { it.name }
    )
}
