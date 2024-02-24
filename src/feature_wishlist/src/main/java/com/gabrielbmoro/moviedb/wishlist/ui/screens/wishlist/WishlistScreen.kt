package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabrielbmoro.moviedb.core.ui.mvi.LabelWatcherEffect
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
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()

    ScreenScaffold(
        showTopBar = true,
        appBarTitle = stringResource(id = R.string.wishlist),
        bottomBar = bottomBar,
        snackBarHost = {
            SnackbarHost(snackbarHostState)
        }
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
                        onDeleteMovie = { movie ->
                            viewModel.accept(WishlistUserIntent.DeleteMovie(movie))
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }
    }

    LabelWatcherEffect(
        labelFlow = viewModel.label,
        onLabelPublished = { userMessage ->
            userMessage?.let {
                snackbarHostState.showSnackbar(message = it)
            }
        }
    )

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.load()
        }
    )
}
