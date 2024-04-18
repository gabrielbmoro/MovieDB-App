package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import `MovieDB-Android`.resources.MR
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gabrielbmoro.moviedb.desingsystem.images.EmptyState
import com.gabrielbmoro.moviedb.desingsystem.loaders.BubbleLoader
import com.gabrielbmoro.moviedb.desingsystem.scaffold.ScreenScaffold
import com.gabrielbmoro.moviedb.desingsystem.toolbars.FavoriteTabIndex
import com.gabrielbmoro.moviedb.desingsystem.toolbars.NavigationBottomBar
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import com.gabrielbmoro.moviedb.wishlist.ui.widgets.MovieList
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform

class WishlistScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<WishlistScreenModel>()
        val uiState = viewModel.uiState.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }
        val lazyListState = rememberLazyListState()

        val successDeleteMessage = stringResource(MR.strings.delete_success_message)
        val errorDeleteMessage = stringResource(MR.strings.delete_fail_message)

        val navigator = LocalNavigator.currentOrThrow

        val coroutineScope = rememberCoroutineScope()

        ScreenScaffold(
            showTopBar = true,
            appBarTitle = stringResource(MR.strings.wishlist),
            bottomBar = {
                NavigationBottomBar(
                    currentTabIndex = FavoriteTabIndex,
                    onSelectFavoriteTab = {
                        coroutineScope.launch {
                            lazyListState.scrollToItem(0)
                        }
                    },
                    onSelectMoviesTab = {
                        val moviesScreen =
                            KoinPlatform.getKoin().get<Screen>(
                                qualifier = named(NavigationDestinations.MOVIES),
                            )
                        navigator.push(moviesScreen)
                    },
                )
            },
            snackBarHost = {
                SnackbarHost(snackbarHostState)
            },
        ) {
            when {
                uiState.value.isLoading -> {
                    BubbleLoader(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                uiState.value.favoriteMovies?.isEmpty() == true -> {
                    EmptyState(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                else -> {
                    if (uiState.value.favoriteMovies != null) {
                        MovieList(
                            moviesList = uiState.value.favoriteMovies!!,
                            onSelectMovie = {
                                val detailsScreen =
                                    KoinPlatform.getKoin().get<Screen>(
                                        qualifier = named(NavigationDestinations.DETAILS),
                                        parameters = { parametersOf(it.id) },
                                    )
                                navigator.push(detailsScreen)
                            },
                            lazyListState = lazyListState,
                            onDeleteMovie = { movie ->
                                viewModel.accept(WishlistUserIntent.DeleteMovie(movie))
                            },
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .align(Alignment.TopCenter),
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
                    viewModel.accept(WishlistUserIntent.ResultMessageReset)
                    viewModel.accept(WishlistUserIntent.LoadMovies)
                }
            },
        )

        LaunchedEffect(Unit) {
            viewModel.accept(WishlistUserIntent.LoadMovies)
        }
    }
}
