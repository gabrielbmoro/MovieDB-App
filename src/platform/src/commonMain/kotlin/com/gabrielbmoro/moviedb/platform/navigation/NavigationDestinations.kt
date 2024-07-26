package com.gabrielbmoro.moviedb.platform.navigation

import androidx.navigation.NavHostController

object NavigationDestinations {
    const val SEARCH = "search"
    const val DETAILS = "details"
    const val MOVIES = "movies"
    const val WISHLIST = "wishlist"
}

fun NavHostController.navigateToDetails(movieId: Long) {

}

fun NavHostController.navigateToMovies() {

}

fun NavHostController.navigateToSearch() {

}

fun NavHostController.navigateToWishlist() {

}