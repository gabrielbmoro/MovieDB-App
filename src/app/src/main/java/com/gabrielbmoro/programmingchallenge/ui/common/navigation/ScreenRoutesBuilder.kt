package com.gabrielbmoro.programmingchallenge.ui.common.navigation

import android.net.Uri
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.google.gson.Gson

object ScreenRoutesBuilder {

    const val DETAILED_MOVIE_ROUTE = "detailedMovie"
    const val DETAILED_MOVIE_ARGUMENT_KEY = "targetMovie"

    fun detailedMovieRoute(movie: Movie): String {
        val json = Uri.encode(Gson().toJson(movie))
        return "$DETAILED_MOVIE_ROUTE/$json"
    }
}