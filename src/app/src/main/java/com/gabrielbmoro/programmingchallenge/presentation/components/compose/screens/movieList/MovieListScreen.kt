package com.gabrielbmoro.programmingchallenge.presentation.components.compose.screens.movieList

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.EmptyState
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.MovieCard
import com.gabrielbmoro.programmingchallenge.presentation.detailedScreen.MovieDetailedActivity
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.google.accompanist.swiperefresh.SwipeRefresh

@Composable
private fun MoviesList(
    movies: List<Movie>,
    requestMoreCallback: (() -> Unit),
    context: Context,
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
                votes = movie.votesAverage
            ) {
                context.startActivity(
                    MovieDetailedActivity.newIntent(context, movie)
                )
            }
        }
    }
}

@Composable
fun TopRatedMoviesScreen(
    viewModel: MovieListViewModel = hiltViewModel<MovieListViewModel>().apply {
        setup(MovieListType.TopRated)
    }
) {
    MovieListScreen(viewModel)
}

@Composable
fun PopularMoviesScreen(
    viewModel: MovieListViewModel = hiltViewModel<MovieListViewModel>().apply {
        setup(MovieListType.Popular)
    }
) {
    MovieListScreen(viewModel)
}

@Composable
fun FavoriteMoviesScreen(
    viewModel: MovieListViewModel = hiltViewModel<MovieListViewModel>().apply {
        setup(MovieListType.Favorite)
    }
) {
    MovieListScreen(viewModel)
}

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel
) {
    val moviesState = viewModel.movies.observeAsState()
    val loadingState = viewModel.loading.observeAsState()

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
                    context = LocalContext.current,
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                )
            }
        }

        if (loadingState.value == true) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colors.secondary
            )
        }
    }
}