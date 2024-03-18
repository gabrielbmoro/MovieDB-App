package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gabrielbmoro.moviedb.core.ui.navigation.FavoriteTabIndex
import com.gabrielbmoro.moviedb.core.ui.navigation.MovieDBNavDestinations
import com.gabrielbmoro.moviedb.core.ui.navigation.NavigationBottomBar
import com.gabrielbmoro.moviedb.core.ui.widgets.BubbleLoader
import com.gabrielbmoro.moviedb.core.ui.widgets.EmptyState
import com.gabrielbmoro.moviedb.core.ui.widgets.ScreenScaffold
import com.gabrielbmoro.moviedb.feature.wishlist.R
import com.gabrielbmoro.moviedb.wishlist.ui.widgets.MovieList
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform

class WishlistScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<WishlistScreenModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        val snackbarHostState = remember { SnackbarHostState() }
        val lazyListState = rememberLazyListState()

        val successDeleteMessage = stringResource(id = R.string.delete_success_message)
        val errorDeleteMessage = stringResource(id = R.string.delete_fail_message)

        val navigator = LocalNavigator.currentOrThrow
        val navDestinations = remember {
            KoinPlatform.getKoin().get<MovieDBNavDestinations>()
        }

        val coroutineScope = rememberCoroutineScope()

        ScreenScaffold(
            showTopBar = true,
            appBarTitle = stringResource(id = R.string.wishlist),
            bottomBar = {
                NavigationBottomBar(
                    currentTabIndex = FavoriteTabIndex,
                    onSelectFavoriteTab = {
                        coroutineScope.launch {
                            lazyListState.scrollToItem(0)
                        }
                    },
                    onSelectMoviesTab = {
                        navigator.push(
                            navDestinations.moviesScreen()
                        )
                    }
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
                    uiState.value.favoriteMovies?.let { moviesList ->
                        MovieList(
                            moviesList = moviesList,
                            onSelectMovie = {
                                navigator.push(
                                    navDestinations.detailsScreen(it)
                                )
                            },
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

        LaunchedEffect(
            key1 = uiState.value,
            block = {
                uiState.value.isSuccessResult?.let { isSuccessResult ->
                    val resultMessage = if (isSuccessResult) {
                        successDeleteMessage
                    } else {
                        errorDeleteMessage
                    }
                    snackbarHostState.showSnackbar(resultMessage)
                    viewModel.accept(WishlistUserIntent.ResultMessageReset)
                }

                viewModel.accept(WishlistUserIntent.LoadMovies)
            }
        )
    }

}