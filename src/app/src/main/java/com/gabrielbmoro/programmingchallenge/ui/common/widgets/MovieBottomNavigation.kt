package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

    BottomNavigation(
        modifier = Modifier.height(56.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                selectedContentColor = MaterialTheme.colors.onSurface,
                unselectedContentColor = Color.Gray,
                label = {
                    Text(stringResource(id = item.title))
                },
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = null
                    )
                },
                selected = currentRoute == item.route,
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