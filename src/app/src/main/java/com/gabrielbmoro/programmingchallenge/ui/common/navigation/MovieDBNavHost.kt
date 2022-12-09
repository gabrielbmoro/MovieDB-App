package com.gabrielbmoro.programmingchallenge.ui.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.ui.screens.details.DetailsScreen
import com.gabrielbmoro.programmingchallenge.ui.screens.home.FavoriteMoviesTab
import com.gabrielbmoro.programmingchallenge.ui.screens.home.MovieListViewModel
import com.gabrielbmoro.programmingchallenge.ui.screens.home.PopularMoviesTab
import com.gabrielbmoro.programmingchallenge.ui.screens.home.TopRatedMoviesTab
import com.gabrielbmoro.programmingchallenge.ui.screens.settings.SettingsScreen

@Composable
fun MovieDBNavHost(
    navController: NavHostController,
    moviesListViewModel: MovieListViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.TopRatedMovies.route,
    ) {

        composable(
            route = NavigationItem.TopRatedMovies.route
        ) {
            TopRatedMoviesTab(navController, moviesListViewModel)
        }
        composable(route = NavigationItem.PopularMovies.route) {
            PopularMoviesTab(navController, moviesListViewModel)
        }
        composable(route = NavigationItem.FavoriteMovies.route) {
            FavoriteMoviesTab(navController, moviesListViewModel)
        }
        composable(
            route = "${ScreenRoutesBuilder.DETAILED_MOVIE_ROUTE}/{${ScreenRoutesBuilder.DETAILED_MOVIE_ARGUMENT_KEY}}",
            arguments = listOf(
                navArgument(ScreenRoutesBuilder.DETAILED_MOVIE_ARGUMENT_KEY) {
                    type = NavMovieType()
                }
            ),
        ) {
            val movie =
                it.arguments?.getParcelable<Movie>(ScreenRoutesBuilder.DETAILED_MOVIE_ARGUMENT_KEY)
                    ?: throw IllegalArgumentException("Type should be movie")

            DetailsScreen(navController, movie)
        }
        composable(route = NavigationItem.SettingsScreen.route) {
            SettingsScreen(navController)
        }
    }
}