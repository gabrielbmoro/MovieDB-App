package com.gabrielbmoro.moviedb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gabrielbmoro.moviedb.core.ui.parcelableOf
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreen
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieScreen
import com.gabrielbmoro.moviedb.repository.model.Movie
import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchScreen
import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistScreen

@Composable
fun MovieDBNavHost(
    navController: NavHostController
) {
    val navigateToDetailsScreen: (Movie) -> Unit = { movie ->
        navController.navigate(
            NavigationItem.DetailsScreen(movie).route
        )
    }

    val bottomBar: @Composable (() -> Unit) = {
        MovieBottomNavigationBar(
            navController = navController,
            scrollToTop = {
            }
        )
    }

    NavHost(
        navController = navController,
        startDestination = NavigationItem.Movies.route
    ) {
        composable(
            route = NavigationItem.Movies.route
        ) {
            MovieScreen(
                bottomBar = bottomBar,
                navigateToDetailsScreen = navigateToDetailsScreen,
                navigateToSearchScreen = {
                    navController.navigate(ScreenRoutesBuilder.SEARCH_ROUTE)
                }
            )
        }

        composable(route = NavigationItem.WishList.route) {
            WishlistScreen(
                navigateToDetailsScreen = navigateToDetailsScreen,
                bottomBar = bottomBar
            )
        }

        composable(
            route = ScreenRoutesBuilder.SEARCH_ROUTE
        ) {
            SearchScreen()
        }

        composable(
            route = "${ScreenRoutesBuilder.DETAILED_MOVIE_ROUTE}/{${ScreenRoutesBuilder.DETAILED_MOVIE_ARGUMENT_KEY}}",
            arguments = listOf(
                navArgument(ScreenRoutesBuilder.DETAILED_MOVIE_ARGUMENT_KEY) {
                    type = NavMovieType()
                }
            )
        ) {
            val movie = it.arguments?.parcelableOf(
                ScreenRoutesBuilder.DETAILED_MOVIE_ARGUMENT_KEY,
                Movie::class.java
            ) ?: throw IllegalArgumentException("Type should be movie")

            DetailsScreen(
                onBackEvent = {
                    navController.navigateUp()
                },
                movie = movie
            )
        }
    }
}
