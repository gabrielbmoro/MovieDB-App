package com.gabrielbmoro.moviedb.ui.screens.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gabrielbmoro.moviedb.R
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.ui.common.navigation.NavigationItem
import com.gabrielbmoro.moviedb.ui.common.widgets.MoviesCarousel
import com.gabrielbmoro.moviedb.ui.common.widgets.ScreenScaffold
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

    ScreenScaffold(
        areBarsVisible = uiState.value.areBarsVisible,
        appBarTitle = stringResource(id = R.string.movies_title),
        navController = navController,
        scrollToTop = {
            coroutineScope.launch {
                scrollState.scrollTo(0)
            }
        },
        updateScrollPosition = {
            viewModel.updateScrollPosition(it.y)
        }
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