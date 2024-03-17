package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.gabrielbmoro.moviedb.core.ui.widgets.ScreenScaffold
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.feature.movies.R
import com.gabrielbmoro.moviedb.movies.ui.widgets.MoviesCarousel

class MoviesScreen(
    private val navigateToSearchScreen: () -> Unit,
    private val navigateToDetailsScreen: (Movie) -> Unit,
    private val bottomBar: @Composable () -> Unit
) : Screen{
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MoviesViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        val lazyListState = rememberLazyListState()
        val showTopBar by remember {
            derivedStateOf {
                lazyListState.firstVisibleItemIndex == 0
            }
        }

        ScreenScaffold(
            showTopBar = showTopBar,
            appBarTitle = stringResource(id = R.string.movies),
            searchEvent = navigateToSearchScreen,
            bottomBar = bottomBar
        ) {
            LazyColumn(
                state = lazyListState,
                content = {

                    item {
                        MoviesCarousel(
                            movies = uiState.value.nowPlayingMovies,
                            onSelectMovie = navigateToDetailsScreen,
                            onRequestMore = {
                                viewModel.accept(Intent.RequestMoreNowPlayingMovies)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            title = stringResource(id = R.string.now_playing)
                        )
                    }

                    item {
                        MoviesCarousel(
                            movies = uiState.value.popularMovies,
                            onSelectMovie = navigateToDetailsScreen,
                            onRequestMore = {
                                viewModel.accept(Intent.RequestMorePopularMovies)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            title = stringResource(id = R.string.popular)
                        )
                    }

                    item {
                        MoviesCarousel(
                            movies = uiState.value.topRatedMovies,
                            onSelectMovie = navigateToDetailsScreen,
                            onRequestMore = {
                                viewModel.accept(Intent.RequestMoreTopRatedMovies)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            title = stringResource(id = R.string.top_rated)
                        )
                    }

                    item {
                        MoviesCarousel(
                            movies = uiState.value.upComingMovies,
                            onSelectMovie = navigateToDetailsScreen,
                            onRequestMore = {
                                viewModel.accept(Intent.RequestMoreUpComingMovies)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            title = stringResource(id = R.string.upcoming)
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
