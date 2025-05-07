package com.gabrielbmoro.moviedb.movies.domain.mapper

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieCardInfo
import com.gabrielbmoro.moviedb.platform.Mapper

class MovieMapper: Mapper<Movie, MovieCardInfo> {

    override fun map(input: Movie): MovieCardInfo = input.run {
        MovieCardInfo(
            movieId = id,
            movieTitle = title,
            moviePosterUrl = posterImageUrl.orEmpty(),
        )
    }
}
