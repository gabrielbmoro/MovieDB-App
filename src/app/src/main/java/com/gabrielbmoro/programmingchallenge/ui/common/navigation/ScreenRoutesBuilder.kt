package com.gabrielbmoro.programmingchallenge.ui.common.navigation

import android.net.Uri
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.google.gson.Gson

object ScreenRoutesBuilder {

    const val DETAILED_MOVIE_ROUTE = "detailedMovie"
    const val DETAILED_MOVIE_ARGUMENT_KEY = "targetMovie"
    const val SETTINGS_ROUTE = "settings"

    private const val HOME_SCREEN_ROUTE = "home"
    const val FAVORITE_MOVIES_ROUTE = "$HOME_SCREEN_ROUTE/favoriteMovies"
    const val TOP_RATED_MOVIES_ROUTE = "$HOME_SCREEN_ROUTE/topRatedMovies"
    const val POPULAR_MOVIES_ROUTE = "$HOME_SCREEN_ROUTE/popularMoviesRoute"

    fun detailedMovieRoute(movie: Movie): String {
        val json = Uri.encode(Gson().toJson(movie))
        return "$DETAILED_MOVIE_ROUTE/$json"
    }
}