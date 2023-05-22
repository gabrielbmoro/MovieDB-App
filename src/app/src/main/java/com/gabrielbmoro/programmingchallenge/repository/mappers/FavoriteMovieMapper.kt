package com.gabrielbmoro.programmingchallenge.repository.mappers

import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.room.dto.FavoriteMovieDTO

class FavoriteMovieMapper constructor() {

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
            releaseDate = movie.releaseDate
        )
    }
}