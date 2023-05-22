package com.gabrielbmoro.programmingchallenge.ui.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gabrielbmoro.programmingchallenge.ui.common.parcelableOf
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.ui.screens.details.DetailsScreen
import com.gabrielbmoro.programmingchallenge.ui.screens.details.DetailsScreenViewModel
import com.gabrielbmoro.programmingchallenge.ui.screens.home.BaseHomeScreenTab
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
            BaseHomeScreenTab(navController, movieType = MovieListType.TOP_RATED)
        }
        composable(route = NavigationItem.FavoriteMovies.route) {
            BaseHomeScreenTab(navController, movieType = MovieListType.FAVORITE)
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