package com.gabrielbmoro.moviedb.repository.mappers

import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.repository.room.dto.FavoriteMovieDTO

class FavoriteMovieMapper {

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