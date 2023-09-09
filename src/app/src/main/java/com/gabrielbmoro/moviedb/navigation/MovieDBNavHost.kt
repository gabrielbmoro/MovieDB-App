package com.gabrielbmoro.moviedb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gabrielbmoro.moviedb.repository.model.Movie
import com.gabrielbmoro.moviedb.core.ui.parcelableOf
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreen
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreenViewModel
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MovieScreen
import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersHolder

@Composable
fun MovieDBNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Movies.route,
    ) {

        composable(
            route = NavigationItem.Movies.route
        ) {
            MovieScreen(
                bottomBar = {
                    MovieBottomNavigationBar(
                        navController = navController,
                        scrollToTop = {

                        }
                    )
                },
                navigateToDetailsScreen = { movie ->
                    navController.navigate(
                        NavigationItem.DetailsScreen(movie).route
                    )
                }
            )
        }

        composable(route = NavigationItem.WishList.route) {
            WishlistScreen(
                navigateToDetailsScreen = {

                },
                bottomBar = {

                }
            )
        }

        composable(
            route = "${ScreenRoutesBuilder.DETAILED_MOVIE_ROUTE}/{${ScreenRoutesBuilder.DETAILED_MOVIE_ARGUMENT_KEY}}",
            arguments = listOf(
                navArgument(ScreenRoutesBuilder.DETAILED_MOVIE_ARGUMENT_KEY) {
                    type = NavMovieType()
                }
            ),
        ) {
            val movie = it.arguments?.parcelableOf(
                ScreenRoutesBuilder.DETAILED_MOVIE_ARGUMENT_KEY,
                Movie::class.java
            ) ?: throw IllegalArgumentException("Type should be movie")

            val viewModel = koinViewModel<DetailsScreenViewModel>(parameters = {
                ParametersHolder(
                    mutableListOf(
                        movie
                    )
                )
            })
            DetailsScreen(
                onBackEvent = {

                },
                viewModel = viewModel
            )
        }
    }
}