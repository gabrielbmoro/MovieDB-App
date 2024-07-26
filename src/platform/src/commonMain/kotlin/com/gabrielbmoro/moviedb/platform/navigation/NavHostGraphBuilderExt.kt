package com.gabrielbmoro.moviedb.platform.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.addMoviesScreen(
    content: @Composable AnimatedContentScope.() -> Unit
) {
    composable(
        route = Screen.Movies.route,
        content = {
            content()
        }
    )
}

fun NavGraphBuilder.addMovieDetailsScreen(
    content: @Composable AnimatedContentScope.(movieId: Long) -> Unit
) {
    composable(
        route = Screen.Details.route.plus("?$DETAILS_MOVIE_ID_ARGUMENT_KEY={movieId}"),
        arguments = listOf(
            navArgument(DETAILS_MOVIE_ID_ARGUMENT_KEY) {
                type = NavType.LongType
            }
        )
    ) {
        val movieId = it.arguments?.getLong(DETAILS_MOVIE_ID_ARGUMENT_KEY)
            ?: throw IllegalStateException("No movieId found in backstack entry")

        content(movieId)
    }
}

fun NavGraphBuilder.addWishlistScreen(
    content: @Composable AnimatedContentScope.() -> Unit
) {
    composable(Screen.Wishlist.route) {
        content()
    }
}

fun NavGraphBuilder.addSearchScreen(
    content: @Composable AnimatedContentScope.(query: String) -> Unit
) {
    composable(
        route = Screen.Details.route.plus("?$SEARCH_QUERY_ARGUMENT_KEY={query}"),
        arguments = listOf(
            navArgument(SEARCH_QUERY_ARGUMENT_KEY) {
                type = NavType.StringType
            }
        )
    ) {
        val query = it.arguments?.getString(SEARCH_QUERY_ARGUMENT_KEY) ?: ""
        content(query)
    }
}