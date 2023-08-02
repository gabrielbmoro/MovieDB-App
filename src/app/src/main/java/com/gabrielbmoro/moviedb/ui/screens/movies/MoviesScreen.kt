package com.gabrielbmoro.moviedb.ui.screens.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

    val lazyListState = rememberLazyListState()
    val showTopBar by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val onSelectMovie: ((Movie) -> Unit) = { movie ->
        navController.navigate(
            NavigationItem.DetailsScreen(movie).route
        )
    }

    ScreenScaffold(
        showTopBar = showTopBar,
        appBarTitle = stringResource(id = R.string.movies),
        navController = navController,
        scrollToTop = {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(0)
            }
        }
    ) {
        LazyColumn(
            state = lazyListState,
            content = {
                items(uiState.value.carousels.size) {
                    val carousel = uiState.value.carousels[it]
                    MoviesCarousel(
                        content = carousel,
                        onSelectMovie = onSelectMovie,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.Top)
        )
    }
}