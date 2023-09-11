package com.gabrielbmoro.moviedb.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MovieBottomNavigationBar(
    navController: NavController,
    scrollToTop: (() -> Unit)
) {
    val items = listOf(
        NavigationItem.Movies,
        NavigationItem.WishList
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