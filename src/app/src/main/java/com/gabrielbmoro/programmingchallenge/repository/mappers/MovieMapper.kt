package com.gabrielbmoro.programmingchallenge.repository.mappers

import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.MovieResponse
import com.gabrielbmoro.programmingchallenge.repository.room.dto.FavoriteMovieDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieMapper @Inject constructor() {

    fun mapResponse(movieResponse: MovieResponse): Movie {
        return Movie(
            votesAverage = movieResponse.votesAverage ?: 0f,
            title = movieResponse.title ?: "",
            posterImageUrl = movieResponse.posterPath ?: "",
            backdropImageUrl = movieResponse.backdropPath ?: "",
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
            posterImageUrl = favoriteMovie.posterImageUrl,
            backdropImageUrl = favoriteMovie.backdropImageUrl,
            overview = favoriteMovie.overview,
            title = favoriteMovie.title,
            votesAverage = favoriteMovie.votesAverage
        )
    }
}