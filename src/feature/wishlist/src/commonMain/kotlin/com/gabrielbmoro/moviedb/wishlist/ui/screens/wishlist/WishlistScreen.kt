@file:Suppress("LongMethod")

package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.gabrielbmoro.moviedb.SharedRes
import com.gabrielbmoro.moviedb.desingsystem.images.EmptyState
import com.gabrielbmoro.moviedb.desingsystem.loaders.BubbleLoader
import com.gabrielbmoro.moviedb.desingsystem.scaffold.ScreenScaffold
import com.gabrielbmoro.moviedb.desingsystem.toolbars.FavoriteTabIndex
import com.gabrielbmoro.moviedb.desingsystem.toolbars.NavigationBottomBar
import com.gabrielbmoro.moviedb.platform.navigation.navigateToDetails
import com.gabrielbmoro.moviedb.platform.navigation.navigateToMovies
import com.gabrielbmoro.moviedb.wishlist.ui.widgets.DeleteConfirmationDialog
import com.gabrielbmoro.moviedb.wishlist.ui.widgets.MovieList
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = koinViewModel(),
    navigator: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()

    val successDeleteMessage = stringResource(SharedRes.strings.delete_success_message)
    val errorDeleteMessage = stringResource(SharedRes.strings.delete_fail_message)

    val coroutineScope = rememberCoroutineScope()

    ScreenScaffold(
        showTopBar = true,
        appBarTitle = stringResource(SharedRes.strings.wishlist),
        bottomBar = {
            NavigationBottomBar(
                currentTabIndex = FavoriteTabIndex,
                onSelectFavoriteTab = {
                    coroutineScope.launch {
                        lazyListState.scrollToItem(0)
                    }
                },
                onSelectMoviesTab = navigator::navigateToMovies
            )
        },
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
                if (uiState.value.favoriteMovies != null) {
                    MovieList(
                        moviesList = uiState.value.favoriteMovies!!,
                        onSelectMovie = { selectedMovie ->
                            navigator.navigateToDetails(selectedMovie.id)
                        },
                        lazyListState = lazyListState,
                        onDeleteMovie = { movie ->
                            viewModel.execute(WishlistUserIntent.PrepareToDeleteMovie(movie))
                        },
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }
    }

    LaunchedEffect(
        key1 = uiState.value,
        block = {
            if (uiState.value.isSuccessResult != null) {
                val resultMessage =
                    if (uiState.value.isSuccessResult == true) {
                        successDeleteMessage
                    } else {
                        errorDeleteMessage
                    }
                snackbarHostState.showSnackbar(resultMessage)
                viewModel.execute(WishlistUserIntent.ResultMessageReset)
                viewModel.execute(WishlistUserIntent.LoadMovies)
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.execute(WishlistUserIntent.LoadMovies)
    }

    DeleteConfirmationDialog(
        onDismissRequest = {
            viewModel.execute(
                WishlistUserIntent.HideConfirmDeleteDialog
            )
        },
        onPositiveAction = {
            viewModel.execute(
                WishlistUserIntent.DeleteMovie
            )
        },
        visible = uiState.value.isDeleteAlertDialogVisible
    )
}
