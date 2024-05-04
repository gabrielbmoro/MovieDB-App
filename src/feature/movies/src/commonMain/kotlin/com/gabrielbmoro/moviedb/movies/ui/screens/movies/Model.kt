package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.domain.entities.Movie

data class MoviesUIState(
    val nowPlayingMovies: List<Movie>,
    val popularMovies: List<Movie>,
    val topRatedMovies: List<Movie>,
    val upComingMovies: List<Movie>
)

sealed class Intent {
    data object RequestMoreUpComingMovies : Intent()

    data object RequestMorePopularMovies : Intent()

    data object RequestMoreTopRatedMovies : Intent()

    data object RequestMoreNowPlayingMovies : Intent()
}
