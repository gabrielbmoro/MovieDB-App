package com.gabrielbmoro.programmingchallenge.presentation.components.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.screens.movieList.*
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.theme.BottomBackgroundAndContentColors

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
fun MovieDBAppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.TopRatedMovies.route,
    ) {
        composable(
            route = NavigationItem.TopRatedMovies.route
        ) {
            TopRatedMoviesScreen()
        }
        composable(route = NavigationItem.PopularMovies.route) {
            PopularMoviesScreen()
        }
        composable(route = NavigationItem.FavoriteMovies.route) {
            FavoriteMoviesScreen()
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

    val (backgroundColor, contentColor) = BottomBackgroundAndContentColors()

    BottomNavigation(
        modifier = Modifier.height(56.dp),
        backgroundColor = backgroundColor,
        contentColor = contentColor
    ) {
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