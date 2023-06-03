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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.ui.common.navigation.NavigationItem
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.*
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseHomeScreenTab(
    navController: NavController,
    viewModel: HomeViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val lazyColumnState = rememberLazyListState()

    var showSearchAlert by remember {
        mutableStateOf(false)
    }

    var areBarsVisible by remember {
        mutableStateOf(true)
    }

    val onSelectMovie: ((Movie) -> Unit) = { movie ->
        navController.navigate(
            NavigationItem.DetailsScreen(movie).route
        )
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
                    searchEvent = if (uiState is HomeUIState.FavoriteTabUIState)
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
                val modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                    )
                if (uiState is HomeUIState.FavoriteTabUIState) {
                    (uiState as HomeUIState.FavoriteTabUIState).run {
                        if (isLoading) {
                            BubbleLoader(
                                modifier = Modifier.align(Alignment.Center),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        } else {
                            MovieList(
                                movies = (uiState as HomeUIState.FavoriteTabUIState).favoriteMovies
                                    ?: emptyList(),
                                onSelectMovie = onSelectMovie,
                                modifier = modifier,
                            )
                        }
                    }
                } else {
                    MoviesListPaginated(
                        pagingDataFlow = viewModel.getPaginatedMovies() ?: emptyFlow(),
                        onSelectMovie = onSelectMovie,
                        modifier = modifier,
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
}