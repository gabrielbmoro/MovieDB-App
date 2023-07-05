package com.gabrielbmoro.moviedb.ui.common.navigation

import android.net.Uri
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.google.gson.Gson

object ScreenRoutesBuilder {

    const val DETAILED_MOVIE_ROUTE = "detailedMovie"
    const val DETAILED_MOVIE_ARGUMENT_KEY = "targetMovie"

    private const val HOME_SCREEN_ROUTE = "home"
    const val FAVORITE_MOVIES_ROUTE = "$HOME_SCREEN_ROUTE/favoriteMovies"
    const val MOVIES_ROUTE = "$HOME_SCREEN_ROUTE/moviesRoute"

    fun detailedMovieRoute(movie: Movie): String {
        val json = Uri.encode(Gson().toJson(movie))
        return "$DETAILED_MOVIE_ROUTE/$json"
    }
}