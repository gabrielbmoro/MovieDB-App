package com.gabrielbmoro.moviedb.ui.screens.movies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gabrielbmoro.moviedb.R
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.ui.common.navigation.NavigationItem
import com.gabrielbmoro.moviedb.ui.common.widgets.AppToolbar
import com.gabrielbmoro.moviedb.ui.common.widgets.MovieBottomNavigationBar
import com.gabrielbmoro.moviedb.ui.common.widgets.MoviesCarousel
import kotlinx.coroutines.launch

@Composable
fun MovieScreen(
    navController: NavController,
    viewModel: MoviesViewModel,
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val onSelectMovie: ((Movie) -> Unit) = { movie ->
        navController.navigate(
            NavigationItem.DetailsScreen(movie).route
        )
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = uiState.value.areBarsVisible,
                enter = expandVertically(
                    tween(delayMillis = 200, durationMillis = 500)
                ),
                exit = shrinkVertically()
            ) {
                AppToolbar(
                    title = stringResource(id = R.string.movies_title),
                    backEvent = null,
                    searchEvent = null,
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = uiState.value.areBarsVisible,
                enter = fadeIn(
                    tween(delayMillis = 200, durationMillis = 500)
                ),
                exit = fadeOut()
            ) {
                MovieBottomNavigationBar(
                    navController,
                    scrollToTop = {
                        coroutineScope.launch {
                            scrollState.scrollTo(0)
                        }
                    }
                )
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(
                        top = it.calculateTopPadding(),
                        start = 16.dp,
                        end = 16.dp,
                        bottom = it.calculateBottomPadding()
                    )
                    .fillMaxSize()
                    .nestedScroll(
                        object : NestedScrollConnection {
                            override fun onPreScroll(
                                available: Offset,
                                source: NestedScrollSource
                            ): Offset {
                                viewModel.updateScrollPosition(available.y)
                                return super.onPreScroll(available, source)
                            }
                        },
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopCenter)
                        .verticalScroll(scrollState)
                ) {
                    MoviesCarousel(
                        sectionTitle = stringResource(id = R.string.upcoming_movies_title),
                        movies = uiState.value.upcomingMoviesPagingData,
                        onSelectMovie = onSelectMovie,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    MoviesCarousel(
                        sectionTitle = stringResource(id = R.string.popular_movies_title),
                        movies = uiState.value.popularMoviesPagingData,
                        onSelectMovie = onSelectMovie,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    MoviesCarousel(
                        sectionTitle = stringResource(id = R.string.top_rated_movies_title),
                        movies = uiState.value.topRatedMoviesPagingData,
                        onSelectMovie = onSelectMovie,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                    )
                }
            }
        }
    )
}