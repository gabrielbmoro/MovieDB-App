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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.ui.common.navigation.NavigationItem
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.*
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseHomeScreenTab(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    movieType: MovieListType
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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

    var areBarsVisible by remember {
        mutableStateOf(true)
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = areBarsVisible,
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
                visible = areBarsVisible,
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
                    .nestedScroll(
                        object : NestedScrollConnection {
                            override fun onPreScroll(
                                available: Offset,
                                source: NestedScrollSource
                            ): Offset {
                                areBarsVisible = available.y > 0
                                return super.onPreScroll(available, source)
                            }
                        },
                    )
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
                                coroutineScope.launch {
                                    viewModel.onSearchBy(searchBy)

                                    lazyColumnState.scrollToItem(0, 0)
                                }
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