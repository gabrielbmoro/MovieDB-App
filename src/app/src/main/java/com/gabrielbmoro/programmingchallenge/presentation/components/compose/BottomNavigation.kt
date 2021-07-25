package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie

sealed class NavigationItem(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val route: String
) {
    object TopRatedMovies : NavigationItem(
        icon = R.drawable.ic_photo_album,
        title = R.string.top_rated_movies_title,
        route = "topRatedMovies"
    )

    object PopularMovies : NavigationItem(
        icon = R.drawable.ic_theaters,
        title = R.string.popular_movies_title,
        route = "popularMovies"
    )

    object FavoriteMovies : NavigationItem(
        icon = R.drawable.ic_stars,
        title = R.string.favorite_movies_title,
        route = "favoriteMovies"
    )
}

@Composable
fun Navigation(
    navController: NavHostController,
    topRatedMoviesArgs: Pair<State<List<Movie>?>, (()->Unit)>,
    popularMoviesArgs: Pair<State<List<Movie>?>, (()->Unit)>,
    favoriteMoviesArgs: Pair<State<List<Movie>?>, (()->Unit)>
) {
    NavHost(navController = navController, startDestination = NavigationItem.TopRatedMovies.route) {
        composable(NavigationItem.TopRatedMovies.route) {
            val (topRatedMoviesState, requestMore) = topRatedMoviesArgs
            MovieListScreen(topRatedMoviesState, requestMore)
        }
        composable(NavigationItem.PopularMovies.route) {
            val (popularMoviesState, requestMore) = popularMoviesArgs
            MovieListScreen(popularMoviesState, requestMore)
        }
        composable(NavigationItem.FavoriteMovies.route) {
            val (favoriteMoviesState, requestMore) = favoriteMoviesArgs
            MovieListScreen(favoriteMoviesState, requestMore)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.TopRatedMovies,
        NavigationItem.PopularMovies,
        NavigationItem.FavoriteMovies
    )

    BottomNavigation() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon), contentDescription = stringResource(
                            id = item.title
                        )
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }

                            launchSingleTop = true

                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}