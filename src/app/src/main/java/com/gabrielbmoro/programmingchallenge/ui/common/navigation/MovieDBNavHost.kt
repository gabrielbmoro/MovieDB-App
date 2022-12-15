package com.gabrielbmoro.programmingchallenge.ui.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.ui.screens.details.DetailsScreen
import com.gabrielbmoro.programmingchallenge.ui.screens.home.BaseHomeScreenTab
import com.gabrielbmoro.programmingchallenge.ui.screens.settings.SettingsScreen

@Composable
fun MovieDBNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.TopRatedMovies.route,
    ) {

        composable(
            route = NavigationItem.TopRatedMovies.route
        ) {
            BaseHomeScreenTab(navController, movieType = MovieListType.TopRated)
        }
        composable(route = NavigationItem.PopularMovies.route) {
            BaseHomeScreenTab(navController, movieType = MovieListType.Popular)
        }
        composable(route = NavigationItem.FavoriteMovies.route) {
            BaseHomeScreenTab(navController, movieType = MovieListType.Favorite)
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