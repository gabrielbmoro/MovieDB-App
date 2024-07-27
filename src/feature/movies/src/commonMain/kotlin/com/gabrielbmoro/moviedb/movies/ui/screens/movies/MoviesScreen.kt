@file:Suppress("LongMethod")

package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gabrielbmoro.moviedb.SharedRes
import com.gabrielbmoro.moviedb.desingsystem.scaffold.ScreenScaffold
import com.gabrielbmoro.moviedb.desingsystem.toolbars.MoviesTabIndex
import com.gabrielbmoro.moviedb.desingsystem.toolbars.NavigationBottomBar
import com.gabrielbmoro.moviedb.movies.ui.widgets.MoviesCarousel
import com.gabrielbmoro.moviedb.platform.navigation.navigateToDetails
import com.gabrielbmoro.moviedb.platform.navigation.navigateToSearch
import com.gabrielbmoro.moviedb.platform.navigation.navigateToWishlist
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = koinViewModel(),
    navigator: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val showTopBar by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }
    ScreenScaffold(
        showTopBar = showTopBar,
        appBarTitle = stringResource(SharedRes.strings.movies),
        searchEvent = {
            navigator.navigateToSearch("")
        },
        bottomBar = {
            NavigationBottomBar(
                currentTabIndex = MoviesTabIndex,
                onSelectMoviesTab = {
                    coroutineScope.launch {
                        lazyListState.scrollToItem(0)
                    }
                },
                onSelectFavoriteTab = {
                    navigator.navigateToWishlist()
                }
            )
        }
    ) {
        LazyColumn(
            state = lazyListState,
            content = {
                item {
                    MoviesCarousel(
                        movies = uiState.value.nowPlayingMovies,
                        onSelectMovie = { selectedMovie ->
                            navigator.navigateToDetails(selectedMovie.id)
                        },
                        onRequestMore = {
                            viewModel.execute(Intent.RequestMoreNowPlayingMovies)
                        },
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        title = stringResource(SharedRes.strings.now_playing)
                    )
                }

                item {
                    MoviesCarousel(
                        movies = uiState.value.popularMovies,
                        onSelectMovie = { selectedMovie ->
                            navigator.navigateToDetails(selectedMovie.id)
                        },
                        onRequestMore = {
                            viewModel.execute(Intent.RequestMorePopularMovies)
                        },
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        title = stringResource(SharedRes.strings.popular)
                    )
                }

                item {
                    MoviesCarousel(
                        movies = uiState.value.topRatedMovies,
                        onSelectMovie = { selectedMovie ->
                            navigator.navigateToDetails(selectedMovie.id)
                        },
                        onRequestMore = {
                            viewModel.execute(Intent.RequestMoreTopRatedMovies)
                        },
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        title = stringResource(SharedRes.strings.top_rated)
                    )
                }

                item {
                    MoviesCarousel(
                        movies = uiState.value.upComingMovies,
                        onSelectMovie = { selectedMovie ->
                            navigator.navigateToDetails(selectedMovie.id)
                        },
                        onRequestMore = {
                            viewModel.execute(Intent.RequestMoreUpComingMovies)
                        },
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        title = stringResource(SharedRes.strings.upcoming)
                    )
                }
            },
            modifier =
            Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.Top)
        )
    }
}
