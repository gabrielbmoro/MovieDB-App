package com.gabrielbmoro.moviedb.core.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gabrielbmoro.moviedb.core.R

const val MoviesTabIndex = 0
const val FavoriteTabIndex = 1

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
                    painter = painterResource(id = R.drawable.ic_theaters),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(id = R.string.movies))
            }
        )

        NavigationBarItem(
            selected = currentTabIndex == FavoriteTabIndex,
            onClick = onSelectFavoriteTab,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_stars),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(id = R.string.favorite))
            }
        )
    }
}