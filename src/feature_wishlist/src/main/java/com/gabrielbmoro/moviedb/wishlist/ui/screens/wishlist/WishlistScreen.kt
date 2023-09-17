package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabrielbmoro.moviedb.core.ui.widgets.BubbleLoader
import com.gabrielbmoro.moviedb.core.ui.widgets.EmptyState
import com.gabrielbmoro.moviedb.core.ui.widgets.ScreenScaffold
import com.gabrielbmoro.moviedb.feature.wishlist.R
import com.gabrielbmoro.moviedb.repository.model.Movie
import com.gabrielbmoro.moviedb.wishlist.ui.widgets.MovieList

@Composable
fun WishlistScreen(
    navigateToDetailsScreen: ((Movie) -> Unit),
    bottomBar: @Composable (() -> Unit),
    viewModel: WishlistViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val lazyListState = rememberLazyListState()

    ScreenScaffold(
        showTopBar = true,
        appBarTitle = stringResource(id = R.string.wishlist),
        bottomBar = bottomBar
    ) {
        when {
            uiState.value.isLoading -> {
                BubbleLoader(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            uiState.value.favoriteMovies?.isEmpty() == true -> {
                EmptyState(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                uiState.value.favoriteMovies?.let { moviesList ->
                    MovieList(
                        moviesList = moviesList,
                        onSelectMovie = navigateToDetailsScreen,
                        lazyListState = lazyListState,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.load()
        }
    )
}
