package com.gabrielbmoro.moviedb.repository.mappers

import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.MovieResponse
import com.gabrielbmoro.moviedb.repository.datasources.room.dto.FavoriteMovieDTO
import com.gabrielbmoro.moviedb.repository.model.Movie
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun mapResponse(movieResponse: MovieResponse): Movie {
        return Movie(
            id = movieResponse.id,
            votesAverage = movieResponse.votesAverage ?: 0f,
            title = movieResponse.title ?: "",
            posterImageUrl = movieResponse.posterPath?.let { SMALL_SIZE_IMAGE_ADDRESS.plus(it) },
            backdropImageUrl = movieResponse.backdropPath?.let {
                BIG_SIZE_IMAGE_ADDRESS.plus(
                    movieResponse.backdropPath
                )
            },
            overview = movieResponse.overview ?: "",
            releaseDate = movieResponse.releaseDate ?: "",
            popularity = movieResponse.popularity ?: 0f,
            language = movieResponse.originalLanguage ?: "",
            isFavorite = false
        )
    }

    fun mapFavorite(favoriteMovie: FavoriteMovieDTO): Movie {
        return Movie(
            id = favoriteMovie.movieId,
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