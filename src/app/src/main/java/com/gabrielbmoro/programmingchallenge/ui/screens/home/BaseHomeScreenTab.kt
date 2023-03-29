package com.gabrielbmoro.programmingchallenge.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.ui.common.navigation.NavigationItem
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.*
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseHomeScreenTab(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    movieType: MovieListType
) {
    val uiState by remember { viewModel.uiState }
    val coroutineScope = rememberCoroutineScope()
    val lazyColumnState = rememberLazyListState()
    val movies by remember {
        derivedStateOf { uiState.movies }
    }
    val isLoading by remember {
        derivedStateOf { uiState.isLoading }
    }

    var showSearchAlert by remember {
        mutableStateOf(false)
    }

    val isNotScrolling by remember {
        derivedStateOf { lazyColumnState.isScrollInProgress.not() }
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = isNotScrolling,
                enter = expandVertically(
                    tween(delayMillis = 200, durationMillis = 500)
                ),
                exit = shrinkVertically()
            ) {
                AppToolbar(
                    title = stringResource(id = R.string.app_name),
                    backEvent = null,
                    searchEvent = if (uiState.selectedMovieListType == MovieListType.FAVORITE)
                        null
                    else {
                        {
                            showSearchAlert = !showSearchAlert
                        }
                    }
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isNotScrolling,
                enter = fadeIn(
                    tween(delayMillis = 200, durationMillis = 500)
                ),
                exit = fadeOut()
            ) {
                MovieBottomNavigationBar(
                    navController,
                    scrollToTop = {
                        coroutineScope.launch {
                            lazyColumnState.scrollToItem(0, 0)
                        }
                    }
                )
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxSize()
            ) {
                if (movies != null) {
                    MoviesList(
                        movies = movies!!,
                        requestMoreCallback = { viewModel.requestMore() },
                        onSelectMovie = { movie ->
                            navController.navigate(
                                NavigationItem.DetailsScreen(movie).route
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                            ),
                        lazyListState = lazyColumnState
                    )

                    if (movies!!.isEmpty()) {
                        EmptyState(modifier = Modifier.align(Alignment.Center))
                    }
                }

                if (isLoading) {
                    BubbleLoader(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                if (showSearchAlert) {
                    viewModel.currentSearchType()?.let { searchType ->
                        MovieSearchAlert(
                            onDismissAlert = {
                                showSearchAlert = false
                            },
                            onSearch = { searchBy ->
                                viewModel.onSearchBy(searchBy)
                            },
                            searchType = searchType
                        )
                    }
                }
            }
        }
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.setup(movieType)
    })
}