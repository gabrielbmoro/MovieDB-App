package com.gabrielbmoro.programmingchallenge.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.BubbleLoader
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.EmptyState
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.MovieCard
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.MovieBottomNavigationBar
import com.gabrielbmoro.programmingchallenge.ui.common.navigation.NavigationItem
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.AppToolbar
import com.google.accompanist.swiperefresh.SwipeRefresh

@Composable
private fun MoviesList(
    movies: List<Movie>,
    requestMoreCallback: (() -> Unit),
    onSelectMovie: ((Movie) -> Unit),
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    if (listState.firstVisibleItemIndex == movies.lastIndex - 2) {
        requestMoreCallback.invoke()
    }

    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        itemsIndexed(movies) { index, movie ->
            if (index > 0) {
                Box(modifier = Modifier.height(16.dp))
            }

            MovieCard(
                imageUrl = movie.imageUrl,
                title = movie.title,
                releaseDate = movie.releaseDate,
                votes = movie.votesAverage,
                onClick = { onSelectMovie(movie) }
            )
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel(),
    movieType: MovieListType
) {
    val moviesState = viewModel.movies.collectAsState()
    val loadingState = viewModel.loading.collectAsState()

    Scaffold(
        topBar = {
            AppToolbar(
                title = stringResource(id = com.gabrielbmoro.programmingchallenge.R.string.app_name),
                backEvent = null,
                settingsEvent = {}
            )
        },
        bottomBar = { MovieBottomNavigationBar(navController) },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                if (moviesState.value?.isEmpty() == true) {
                    EmptyState(modifier = Modifier.align(Alignment.Center))
                } else if (moviesState.value?.isNotEmpty() == true) {
                    SwipeRefresh(
                        state = viewModel.swipeRefreshLiveData,
                        onRefresh = { viewModel.refresh() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        MoviesList(
                            movies = moviesState.value ?: emptyList(),
                            requestMoreCallback = { viewModel.requestMore() },
                            onSelectMovie = { movie ->
                                navController.navigate(
                                    NavigationItem.DetailsScreen(movie).route
                                )
                            },
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 70.dp
                            ),
                        )
                    }
                }

                if (loadingState.value) {
                    BubbleLoader(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
            LaunchedEffect(key1 = Unit, block = {
                viewModel.setup(movieType)
            })
        }
    )
}