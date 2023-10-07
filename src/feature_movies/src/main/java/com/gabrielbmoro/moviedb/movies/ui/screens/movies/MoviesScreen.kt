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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabrielbmoro.moviedb.core.ui.widgets.ScreenScaffold
import com.gabrielbmoro.moviedb.feature.movies.R
import com.gabrielbmoro.moviedb.movies.ui.widgets.MoviesCarousel
import com.gabrielbmoro.moviedb.repository.model.Movie

@Composable
fun MovieScreen(
    bottomBar: @Composable (() -> Unit),
    navigateToDetailsScreen: ((Movie) -> Unit),
    navigateToSearchScreen: (() -> Unit),
    viewModel: MoviesViewModel = hiltViewModel()
) {
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
                items(uiState.value.carousels.size) {
                    val carousel = uiState.value.carousels[it]
                    MoviesCarousel(
                        content = carousel,
                        onSelectMovie = navigateToDetailsScreen,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
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
