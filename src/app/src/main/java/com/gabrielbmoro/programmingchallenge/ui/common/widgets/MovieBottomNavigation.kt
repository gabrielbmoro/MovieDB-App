package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gabrielbmoro.programmingchallenge.ui.common.navigation.NavigationItem

@Composable
fun MovieBottomNavigationBar(navController: NavController, scrollToTop: (() -> Unit)) {
    val items = listOf(
        NavigationItem.TopRatedMovies,
        NavigationItem.PopularMovies,
        NavigationItem.FavoriteMovies
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = null
                    )
                },
                alwaysShowLabel = true,
                label = {
                    Text(stringResource(id = item.title))
                },
                selected = isSelected,
                onClick = {
                    if (item.route == currentRoute) {
                        scrollToTop()
                    } else {
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
                }
            )
        }
    }
}