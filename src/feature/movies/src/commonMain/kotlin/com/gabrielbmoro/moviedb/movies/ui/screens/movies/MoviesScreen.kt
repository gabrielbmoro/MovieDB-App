@file:Suppress("LongMethod")

package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gabrielbmoro.moviedb.desingsystem.toolbars.AnimatedAppToolbar
import com.gabrielbmoro.moviedb.desingsystem.toolbars.AppToolbarTitle
import com.gabrielbmoro.moviedb.desingsystem.toolbars.MoviesTabIndex
import com.gabrielbmoro.moviedb.desingsystem.toolbars.NavigationBottomBar
import com.gabrielbmoro.moviedb.movies.ui.widgets.FilterMenu
import com.gabrielbmoro.moviedb.movies.ui.widgets.MoviesList
import com.gabrielbmoro.moviedb.platform.navigation.navigateToDetails
import com.gabrielbmoro.moviedb.platform.navigation.navigateToSearch
import com.gabrielbmoro.moviedb.platform.navigation.navigateToWishlist
import kotlinx.coroutines.launch
import moviedbapp.feature.movies.generated.resources.Res
import moviedbapp.feature.movies.generated.resources.movies
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = koinViewModel(),
    navigator: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()

    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()

    val showTopBar by remember {
        derivedStateOf {
            lazyStaggeredGridState.firstVisibleItemIndex == 0
        }
    }

    Scaffold(
        topBar = {
            AnimatedAppToolbar(
                appBar = {
                    AppToolbarTitle(
                        title = stringResource(Res.string.movies),
                        backEvent = null,
                        searchEvent = {
                            navigator.navigateToSearch("")
                        }
                    )
                },
                showTopBar = showTopBar,
            )
        },
        bottomBar = {
            NavigationBottomBar(
                currentTabIndex = MoviesTabIndex,
                onSelectMoviesTab = {
                    coroutineScope.launch {
                        lazyStaggeredGridState.scrollToItem(0)
                    }
                },
                onSelectFavoriteTab = {
                    navigator.navigateToWishlist()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxSize()
        ) {
            FilterMenu(
                modifier = Modifier.fillMaxWidth(),
                menuItems = uiState.value.menuItems,
                onClick = { filterMenuItem ->
                    viewModel.execute(
                        Intent.SelectFilterMenuItem(
                            menuItem = filterMenuItem
                        )
                    )

                    coroutineScope.launch {
                        lazyStaggeredGridState.scrollToItem(0)
                    }
                }
            )

            MoviesList(
                movies = uiState.value.movieCardInfos,
                onSelectMovie = { selectedMovieId ->
                    navigator.navigateToDetails(selectedMovieId)
                },
                onRequestMore = {
                    viewModel.execute(Intent.RequestMoreMovies)
                },
                lazyStaggeredGridState = lazyStaggeredGridState,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
