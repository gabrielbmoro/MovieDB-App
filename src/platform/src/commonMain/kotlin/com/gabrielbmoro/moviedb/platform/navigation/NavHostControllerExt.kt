package com.gabrielbmoro.moviedb.platform.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToDetails(movieId: Long) {
    navigate(Screen.Details.route.detailsRoute(movieId))
}

fun String.detailsRoute(movieId: Long) = plus("?$DETAILS_MOVIE_ID_ARGUMENT_KEY=$movieId")

fun String.searchRoute(query: String) = plus("?$SEARCH_QUERY_ARGUMENT_KEY=$query")

fun NavHostController.navigateToMovies() {
    val result = popBackStack(Screen.Movies.route, inclusive = false)
    if (result.not()) {
        navigate(Screen.Movies.route)
    }
}

fun NavHostController.navigateToSearch(query: String) {
    navigate(Screen.Search.route.searchRoute(query))
}

fun NavHostController.navigateToWishlist() {
    navigate(Screen.Wishlist.route)
}
