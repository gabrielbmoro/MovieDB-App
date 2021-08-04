package com.gabrielbmoro.programmingchallenge.usecases.mappers

import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.room.entities.FavoriteMovieDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMovieMapper @Inject constructor() {

    fun map(id: Int? = null, movie: Movie): FavoriteMovieDTO {
        return FavoriteMovieDTO(
            id = id,
            votesAverage = movie.votesAverage,
            title = movie.title,
            language = movie.language,
            overview = movie.overview,
            imageUrl = movie.imageUrl,
            popularity = movie.popularity,
            releaseDate = movie.releaseDate
        )
    }
}