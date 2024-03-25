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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gabrielbmoro.moviedb.desingsystem.scaffold.ScreenScaffold
import com.gabrielbmoro.moviedb.desingsystem.toolbars.MoviesTabIndex
import com.gabrielbmoro.moviedb.desingsystem.toolbars.NavigationBottomBar
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.ui.widgets.MoviesCarousel
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import kotlinx.coroutines.launch
import moviedb_android.feature.movies.generated.resources.Res
import moviedb_android.feature.movies.generated.resources.movies
import moviedb_android.feature.movies.generated.resources.now_playing
import moviedb_android.feature.movies.generated.resources.popular
import moviedb_android.feature.movies.generated.resources.top_rated
import moviedb_android.feature.movies.generated.resources.upcoming
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform

class MoviesScreen : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MoviesScreenModel>()
        val uiState = viewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow
        val navigateToDetails: ((Movie) -> Unit) = {
            val detailsScreen = KoinPlatform.getKoin().get<Screen>(
                qualifier = named(NavigationDestinations.DETAILS),
                parameters = { parametersOf(it) }
            )
            navigator.push(detailsScreen)
        }

        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        val showTopBar by remember {
            derivedStateOf {
                lazyListState.firstVisibleItemIndex == 0
            }
        }
        ScreenScaffold(
            showTopBar = showTopBar,
            appBarTitle = stringResource(Res.string.movies),
            searchEvent = {
                val searchScreen =
                    KoinPlatform.getKoin().get<Screen>(named(NavigationDestinations.SEARCH))
                navigator.push(searchScreen)
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
                        val wishListScreen = KoinPlatform.getKoin()
                            .get<Screen>(named(NavigationDestinations.WISHLIST))
                        navigator.push(wishListScreen)
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
                            onSelectMovie = {
                                navigateToDetails(it)
                            },
                            onRequestMore = {
                                viewModel.accept(Intent.RequestMoreNowPlayingMovies)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            title = stringResource(Res.string.now_playing)
                        )
                    }

                    item {
                        MoviesCarousel(
                            movies = uiState.value.popularMovies,
                            onSelectMovie = {
                                navigateToDetails(it)
                            },
                            onRequestMore = {
                                viewModel.accept(Intent.RequestMorePopularMovies)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            title = stringResource(Res.string.popular)
                        )
                    }

                    item {
                        MoviesCarousel(
                            movies = uiState.value.topRatedMovies,
                            onSelectMovie = {
                                navigateToDetails(it)
                            },
                            onRequestMore = {
                                viewModel.accept(Intent.RequestMoreTopRatedMovies)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            title = stringResource(Res.string.top_rated)
                        )
                    }

                    item {
                        MoviesCarousel(
                            movies = uiState.value.upComingMovies,
                            onSelectMovie = {
                                navigateToDetails(it)
                            },
                            onRequestMore = {
                                viewModel.accept(Intent.RequestMoreUpComingMovies)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            title = stringResource(Res.string.upcoming)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.Top)
            )
        }
    }
}
