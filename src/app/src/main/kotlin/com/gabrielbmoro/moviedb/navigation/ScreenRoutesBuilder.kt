package com.gabrielbmoro.moviedb.navigation

import android.net.Uri
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.google.gson.Gson

object ScreenRoutesBuilder {

    const val DETAILED_MOVIE_ROUTE = "detailedMovie"
    const val DETAILED_MOVIE_ARGUMENT_KEY = "targetMovie"

    private const val HOME_SCREEN_ROUTE = "home"
    const val WISHLIST_ROUTE = "$HOME_SCREEN_ROUTE/favoriteMovies"
    const val MOVIES_ROUTE = "$HOME_SCREEN_ROUTE/moviesRoute"
    const val SEARCH_ROUTE = "$HOME_SCREEN_ROUTE/search"

    fun detailedMovieRoute(movie: Movie): String {
        val json = Uri.encode(Gson().toJson(movie))
        return "$DETAILED_MOVIE_ROUTE/$json"
    }
}
