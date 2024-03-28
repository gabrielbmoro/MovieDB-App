package com.gabrielbmoro.moviedb.desingsystem.toolbars

import `MovieDB-Android`.resources.MR
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

const val MoviesTabIndex = 0
const val FavoriteTabIndex = 1

@Composable
fun NavigationBottomBar(
    currentTabIndex: Int,
    onSelectMoviesTab: () -> Unit,
    onSelectFavoriteTab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentTabIndex == MoviesTabIndex,
            onClick = onSelectMoviesTab,
            icon = {
                Icon(
                    painter = painterResource(MR.images.ic_movies),
                    contentDescription = null,
                )
            },
            label = {
                Text(stringResource(MR.strings.movies))
            },
        )

        NavigationBarItem(
            selected = currentTabIndex == FavoriteTabIndex,
            onClick = onSelectFavoriteTab,
            icon = {
                Icon(
                    painter = painterResource(MR.images.ic_wishlist),
                    contentDescription = null,
                )
            },
            label = {
                Text(stringResource(MR.strings.favorite))
            },
        )
    }
}
