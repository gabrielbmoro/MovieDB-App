package com.gabrielbmoro.programmingchallenge.ui.common.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.ui.common.theme.BottomBackgroundAndContentColors





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