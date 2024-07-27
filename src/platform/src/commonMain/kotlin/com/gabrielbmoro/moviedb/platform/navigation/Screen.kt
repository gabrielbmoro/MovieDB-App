package com.gabrielbmoro.moviedb.platform.navigation

enum class Screen(val route: String) {
    Movies("movies"),
    Search("Search"),
    Details("details"),
    Wishlist("wishlist")
}

internal const val DETAILS_MOVIE_ID_ARGUMENT_KEY = "movieId"
internal const val SEARCH_QUERY_ARGUMENT_KEY = "query"
