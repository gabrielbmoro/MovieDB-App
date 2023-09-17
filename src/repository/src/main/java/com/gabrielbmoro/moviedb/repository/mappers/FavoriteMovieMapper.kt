package com.gabrielbmoro.moviedb.repository.mappers

import com.gabrielbmoro.moviedb.repository.datasources.room.dto.FavoriteMovieDTO
import com.gabrielbmoro.moviedb.repository.model.Movie
import javax.inject.Inject

class FavoriteMovieMapper @Inject constructor() {

    fun map(id: Int? = null, movie: Movie): FavoriteMovieDTO {
        return FavoriteMovieDTO(
            id = id,
            votesAverage = movie.votesAverage,
            title = movie.title,
            language = movie.language,
            overview = movie.overview,
            posterImageUrl = movie.posterImageUrl,
            backdropImageUrl = movie.backdropImageUrl,
            popularity = movie.popularity,
            releaseDate = movie.releaseDate,
            movieId = movie.id
        )
    }
}
