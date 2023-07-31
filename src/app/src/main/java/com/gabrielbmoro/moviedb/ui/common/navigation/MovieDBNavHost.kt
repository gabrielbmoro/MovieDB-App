package com.gabrielbmoro.moviedb.ui.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gabrielbmoro.moviedb.ui.common.parcelableOf
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.ui.screens.details.DetailsScreen
import com.gabrielbmoro.moviedb.ui.screens.details.DetailsScreenViewModel
import com.gabrielbmoro.moviedb.ui.screens.movies.MovieScreen
import com.gabrielbmoro.moviedb.ui.screens.movies.MoviesViewModel
import com.gabrielbmoro.moviedb.ui.screens.wishlist.WishlistScreen
import com.gabrielbmoro.moviedb.ui.screens.wishlist.WishlistViewModel
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
            val viewModel = koinViewModel<MoviesViewModel>()
            MovieScreen(navController, viewModel)
        }

        composable(route = NavigationItem.FavoriteMovies.route) {
            val viewModel = koinViewModel<WishlistViewModel>()
            WishlistScreen(navController, viewModel)
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
            DetailsScreen(navController, viewModel)
        }
    }
}