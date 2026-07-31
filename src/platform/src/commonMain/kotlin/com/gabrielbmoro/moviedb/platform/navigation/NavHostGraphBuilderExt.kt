package com.gabrielbmoro.moviedb.platform.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.addMoviesScreen(
    content: @Composable AnimatedContentScope.() -> Unit,
) {
    composable(
        route = Screen.Movies.route,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        content = {
            content()
        },
    )
}

fun NavGraphBuilder.addMovieDetailsScreen(
    content: @Composable AnimatedContentScope.(movieId: Long) -> Unit,
) {
    composable(
        route = Screen.Details.route.plus("?$DETAILS_MOVIE_ID_ARGUMENT_KEY={movieId}"),
        arguments = listOf(
            navArgument(DETAILS_MOVIE_ID_ARGUMENT_KEY) {
                type = NavType.LongType
            },
        ),
    ) {
        val movieId = it.savedStateHandle.get<Long>(DETAILS_MOVIE_ID_ARGUMENT_KEY)
            ?: error("No movieId found in backstack entry")

        content(movieId)
    }
}

fun NavGraphBuilder.addWishlistScreen(
    content: @Composable AnimatedContentScope.() -> Unit,
) {
    composable(
        route = Screen.Wishlist.route,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        content = {
            content()
        },
    )
}

fun NavGraphBuilder.addSearchScreen(
    content: @Composable AnimatedContentScope.(query: String?) -> Unit,
) {
    composable(
        route = Screen.Search.route.plus("?$SEARCH_QUERY_ARGUMENT_KEY={query}"),
        arguments = listOf(
            navArgument(SEARCH_QUERY_ARGUMENT_KEY) {
                type = NavType.StringType
                this.nullable = true
            },
        ),
    ) {
        val query = it.savedStateHandle.get<String>(SEARCH_QUERY_ARGUMENT_KEY)
        content(query)
    }
}

fun NavGraphBuilder.addTvShowsScreen(
    content: @Composable AnimatedContentScope.() -> Unit,
) {
    composable(
        route = Screen.TvShows.route,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        content = {
            content()
        },
    )
}

fun NavGraphBuilder.addTvShowDetailsScreen(
    content: @Composable AnimatedContentScope.(tvShowId: Long) -> Unit,
) {
    composable(
        route = Screen.TvShowDetails.route.plus("?$TVSHOW_DETAILS_ID_ARGUMENT_KEY={tvShowId}"),
        arguments = listOf(
            navArgument(TVSHOW_DETAILS_ID_ARGUMENT_KEY) {
                type = NavType.LongType
            },
        ),
    ) {
        val tvShowId = it.savedStateHandle.get<Long>(TVSHOW_DETAILS_ID_ARGUMENT_KEY)
            ?: error("No tvShowId found in backstack entry")

        content(tvShowId)
    }
}
