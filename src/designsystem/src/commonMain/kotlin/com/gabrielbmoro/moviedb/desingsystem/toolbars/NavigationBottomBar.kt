@file:Suppress("TopLevelPropertyNaming", "MagicNumber")

package com.gabrielbmoro.moviedb.desingsystem.toolbars

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moviedbapp.designsystem.generated.resources.Res
import moviedbapp.designsystem.generated.resources.favorite
import moviedbapp.designsystem.generated.resources.ic_movies
import moviedbapp.designsystem.generated.resources.ic_tv_shows
import moviedbapp.designsystem.generated.resources.ic_wishlist
import moviedbapp.designsystem.generated.resources.movies
import moviedbapp.designsystem.generated.resources.tv_shows
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

const val MoviesTabIndex = 0
const val TvShowsTabIndex = 1
const val FavoriteTabIndex = 2

@Composable
fun NavigationBottomBar(
    currentTabIndex: Int,
    onSelectMoviesTab: () -> Unit,
    onSelectTvShowsTab: () -> Unit,
    onSelectFavoriteTab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = currentTabIndex == MoviesTabIndex,
            onClick = onSelectMoviesTab,
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_movies),
                    contentDescription = null,
                )
            },
            label = {
                Text(stringResource(Res.string.movies))
            },
        )

        NavigationBarItem(
            selected = currentTabIndex == TvShowsTabIndex,
            onClick = onSelectTvShowsTab,
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_tv_shows),
                    contentDescription = null,
                )
            },
            label = {
                Text(stringResource(Res.string.tv_shows))
            },
        )

        NavigationBarItem(
            selected = currentTabIndex == FavoriteTabIndex,
            onClick = onSelectFavoriteTab,
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_wishlist),
                    contentDescription = null,
                )
            },
            label = {
                Text(stringResource(Res.string.favorite))
            },
        )
    }
}
