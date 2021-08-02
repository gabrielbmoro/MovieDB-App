package com.gabrielbmoro.programmingchallenge.usecases.mappers

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.MovieResponse
import com.gabrielbmoro.programmingchallenge.repository.room.entities.FavoriteMovieDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieMapper @Inject constructor() {

    fun mapResponse(movieResponse: MovieResponse): Movie {
        return Movie(
            votesAverage = movieResponse.votesAverage ?: 0f,
            title = movieResponse.title ?: "",
            imageUrl = movieResponse.backdropPath ?: "",
            overview = movieResponse.overview ?: "",
            releaseDate = movieResponse.releaseDate ?: "",
            popularity = movieResponse.popularity ?: 0f,
            language = movieResponse.originalLanguage ?: "",
            isFavorite = false
        )
    }

    fun mapFavorite(favoriteMovie: FavoriteMovieDTO): Movie {
        return Movie(
            releaseDate = favoriteMovie.releaseDate,
            isFavorite = true,
            language = favoriteMovie.language,
            popularity = favoriteMovie.popularity,
            imageUrl = favoriteMovie.imageUrl,
            overview = favoriteMovie.overview,
            title = favoriteMovie.title,
            votesAverage = favoriteMovie.votesAverage
        )
    }
}