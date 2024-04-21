package com.gabrielbmoro.moviedb.desingsystem.toolbars

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import com.gabrielbmoro.moviedb.SharedRes

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
                    painter = painterResource(SharedRes.images.ic_movies),
                    contentDescription = null,
                )
            },
            label = {
                Text(stringResource(SharedRes.strings.movies))
            },
        )

        NavigationBarItem(
            selected = currentTabIndex == FavoriteTabIndex,
            onClick = onSelectFavoriteTab,
            icon = {
                Icon(
                    painter = painterResource(SharedRes.images.ic_wishlist),
                    contentDescription = null,
                )
            },
            label = {
                Text(stringResource(SharedRes.strings.favorite))
            },
        )
    }
}
