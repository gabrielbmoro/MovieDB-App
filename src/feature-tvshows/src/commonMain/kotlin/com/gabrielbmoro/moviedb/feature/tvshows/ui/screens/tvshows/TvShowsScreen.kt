@file:Suppress("LongMethod")

package com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.desingsystem.error.ErrorScreen
import com.gabrielbmoro.moviedb.desingsystem.toolbars.AnimatedAppToolbar
import com.gabrielbmoro.moviedb.desingsystem.toolbars.AppToolbarTitle
import com.gabrielbmoro.moviedb.desingsystem.toolbars.NavigationBottomBar
import com.gabrielbmoro.moviedb.desingsystem.toolbars.TvShowsTabIndex
import com.gabrielbmoro.moviedb.feature.tvshows.ui.widgets.FilterMenu
import com.gabrielbmoro.moviedb.feature.tvshows.ui.widgets.ShowsList
import com.gabrielbmoro.moviedb.platform.LocalNavController
import com.gabrielbmoro.moviedb.platform.navigation.navigateToMovies
import com.gabrielbmoro.moviedb.platform.navigation.navigateToTvShowDetails
import com.gabrielbmoro.moviedb.platform.navigation.navigateToWishlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import moviedbapp.feature_tvshows.generated.resources.Res
import moviedbapp.feature_tvshows.generated.resources.tv_shows
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TvShowsScreen() {
    val viewModel = koinViewModel<TvShowsViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val navigator = LocalNavController.current

    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val coroutineScope = rememberCoroutineScope()

    val showTopBar by remember {
        derivedStateOf {
            lazyStaggeredGridState.firstVisibleItemIndex == 0
        }
    }

    Scaffold(
        topBar = {
            if (uiState.errorInfo == null) {
                AnimatedAppToolbar(
                    appBar = {
                        AppToolbarTitle(
                            title = stringResource(Res.string.tv_shows),
                            backEvent = null,
                            searchEvent = null,
                        )
                    },
                    showTopBar = showTopBar,
                )
            }
        },
        bottomBar = {
            if (uiState.errorInfo == null) {
                NavigationBottomBar(
                    currentTabIndex = TvShowsTabIndex,
                    onSelectMoviesTab = navigator::navigateToMovies,
                    onSelectTvShowsTab = {
                        lazyStaggeredGridState.scrollToInit(coroutineScope)
                    },
                    onSelectFavoriteTab = navigator::navigateToWishlist,
                )
            }
        },
    ) {
        Box(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp,
                )
                .fillMaxSize(),
        ) {
            if (uiState.errorInfo != null) {
                ErrorScreen(
                    errorInfo = uiState.errorInfo!!,
                    modifier = Modifier.align(Alignment.Center),
                    onRetry = {
                        lazyStaggeredGridState.scrollToInit(coroutineScope)
                        viewModel.executeIntent(TvShowsIntent.Setup)
                    },
                )
            } else {
                Column {
                    val lazyListState = rememberLazyListState()
                    val isAtStart by rememberIsAtStartState(lazyListState)

                    FilterMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = if (isAtStart) 16.dp else 0.dp),
                        menuItems = uiState.menuItems,
                        lazyListState = lazyListState,
                        onClick = { filterMenuItem ->
                            viewModel.executeIntent(
                                TvShowsIntent.SelectFilterMenuItem(
                                    menuItem = filterMenuItem,
                                ),
                            )
                            lazyStaggeredGridState.scrollToInit(coroutineScope)
                        },
                    )

                    ShowsList(
                        shows = uiState.tvShowCardInfos,
                        onRequestMore = {
                            viewModel.executeIntent(TvShowsIntent.RequestMoreTvShows)
                        },
                        onSelectShow = { tvShowId ->
                            navigator.navigateToTvShowDetails(tvShowId)
                        },
                        lazyStaggeredGridState = lazyStaggeredGridState,
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                }
            }
        }
    }
}

private fun LazyStaggeredGridState.scrollToInit(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        scrollToItem(FIRST_INDEX)
    }
}

@Composable
private fun rememberIsAtStartState(lazyListState: LazyListState): State<Boolean> = remember {
    derivedStateOf { lazyListState.isAtStart() }
}

private const val FIRST_INDEX = 0
private const val NO_OFFSET = 0

private fun LazyListState.isAtStart(): Boolean =
    firstVisibleItemIndex == FIRST_INDEX && firstVisibleItemScrollOffset == NO_OFFSET
