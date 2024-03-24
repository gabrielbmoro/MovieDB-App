package com.gabrielbmoro.moviedb.desingsystem.toolbars

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moviedb_android.design_system.generated.resources.Res
import moviedb_android.design_system.generated.resources.favorite
import moviedb_android.design_system.generated.resources.ic_stars
import moviedb_android.design_system.generated.resources.ic_theaters
import moviedb_android.design_system.generated.resources.movies
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

const val MoviesTabIndex = 0
const val FavoriteTabIndex = 1

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NavigationBottomBar(
    currentTabIndex: Int,
    onSelectMoviesTab: () -> Unit,
    onSelectFavoriteTab: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentTabIndex == MoviesTabIndex,
            onClick = onSelectMoviesTab,
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_theaters),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(Res.string.movies))
            }
        )

        NavigationBarItem(
            selected = currentTabIndex == FavoriteTabIndex,
            onClick = onSelectFavoriteTab,
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_stars),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(Res.string.favorite))
            }
        )
    }
}