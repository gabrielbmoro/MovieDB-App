package com.gabrielbmoro.programmingchallenge.repository.mappers

import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.MovieResponse
import com.gabrielbmoro.programmingchallenge.repository.room.dto.FavoriteMovieDTO

class MovieMapper {

    fun mapResponse(movieResponse: MovieResponse): Movie {
        return Movie(
            id = movieResponse.id,
            votesAverage = movieResponse.votesAverage ?: 0f,
            title = movieResponse.title ?: "",
            posterImageUrl = movieResponse.posterPath?.let { SMALL_SIZE_IMAGE_ADDRESS.plus(it) },
            backdropImageUrl = movieResponse.backdropPath?.let { BIG_SIZE_IMAGE_ADDRESS.plus(movieResponse.backdropPath) },
            overview = movieResponse.overview ?: "",
            releaseDate = movieResponse.releaseDate ?: "",
            popularity = movieResponse.popularity ?: 0f,
            language = movieResponse.originalLanguage ?: "",
            isFavorite = false
        )
    }

    fun mapFavorite(favoriteMovie: FavoriteMovieDTO): Movie {
        return Movie(
            id = favoriteMovie.id?.toLong() ?: System.currentTimeMillis(),
            releaseDate = favoriteMovie.releaseDate,
            isFavorite = true,
            language = favoriteMovie.language,
            popularity = favoriteMovie.popularity,
            posterImageUrl = favoriteMovie.posterImageUrl,
            backdropImageUrl = favoriteMovie.backdropImageUrl,
            overview = favoriteMovie.overview,
            title = favoriteMovie.title,
            votesAverage = favoriteMovie.votesAverage
        )
    }

    companion object {
        const val BIG_SIZE_IMAGE_ADDRESS = "https://image.tmdb.org/t/p/w780"
        const val SMALL_SIZE_IMAGE_ADDRESS = "https://image.tmdb.org/t/p/w300"
    }
}