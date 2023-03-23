package com.gabrielbmoro.programmingchallenge.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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

    Scaffold(
        topBar = {
            AppToolbar(
                title = stringResource(id = R.string.app_name),
                backEvent = null
            )
        },
        bottomBar = {
            MovieBottomNavigationBar(
                navController,
                scrollToTop = {
                    coroutineScope.launch {
                        lazyColumnState.scrollToItem(0, 0)
                    }
                }
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                MoviesList(
                    movies = uiState.movies ?: emptyList(),
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
                            bottom = 70.dp
                        ),
                    lazyListState = lazyColumnState
                )

                if (uiState.movies?.isEmpty() == true) {
                    EmptyState(modifier = Modifier.align(Alignment.Center))
                }

                if (uiState.isLoading) {
                    BubbleLoader(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.setup(movieType)
    })
}