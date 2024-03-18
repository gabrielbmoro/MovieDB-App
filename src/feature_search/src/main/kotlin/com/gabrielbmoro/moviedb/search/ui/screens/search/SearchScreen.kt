package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gabrielbmoro.moviedb.core.ui.navigation.MovieDBNavDestinations
import com.gabrielbmoro.moviedb.core.ui.widgets.CustomAppToolbar
import com.gabrielbmoro.moviedb.search.ui.widgets.MoviesResult
import com.gabrielbmoro.moviedb.search.ui.widgets.SearchInputText
import kotlinx.coroutines.delay
import org.koin.mp.KoinPlatform

class SearchScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<SearchScreenModel>()

        val navigator = LocalNavigator.currentOrThrow
        val navDestinations = remember {
            KoinPlatform.getKoin().get<MovieDBNavDestinations>()
        }

        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        val showKeyboard = remember { mutableStateOf(true) }
        val focusRequester = remember { FocusRequester() }
        val keyboard = LocalSoftwareKeyboardController.current

        Scaffold(
            topBar = {
                CustomAppToolbar(
                    title = {
                        SearchInputText(
                            currentValue = uiState.value.searchQuery,
                            onQueryChanged = {
                                viewModel.accept(SearchUserIntent.SearchInputFieldChanged(it))
                            },
                            onSearchBy = {
                                viewModel.accept(SearchUserIntent.SearchBy(it))
                            },
                            onClearText = {
                                viewModel.accept(SearchUserIntent.ClearSearchField)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            focusRequester = focusRequester
                        )
                    },
                    backEvent = navigator::pop
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
            ) {
                uiState.value.results?.let { movies ->
                    MoviesResult(
                        movies = movies,
                        modifier = Modifier.fillMaxWidth(),
                        navigateToDetailsScreen = { movie ->
                            navigator.push(
                                navDestinations.detailsScreen(movie)
                            )
                        }
                    )
                }
            }
        }

        LaunchedEffect(
            key1 = focusRequester,
            block = {
                if (showKeyboard.value) {
                    focusRequester.requestFocus()
                    delay(500)
                    keyboard?.show()
                }
            }
        )
    }
}
