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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabrielbmoro.moviedb.core.ui.widgets.CustomAppToolbar
import com.gabrielbmoro.moviedb.repository.model.Movie
import com.gabrielbmoro.moviedb.search.ui.widgets.MoviesResult
import com.gabrielbmoro.moviedb.search.ui.widgets.SearchInputText
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToDetailsScreen: ((Movie) -> Unit),
    onBackEvent: (() -> Unit),
) {
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
                        onValueChanged = {
                            viewModel.onSearchQueryChanged(it)
                        },
                        onClearText = {
                            viewModel.onClearSearchQuery()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        focusRequester = focusRequester
                    )
                },
                backEvent = onBackEvent
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
                    navigateToDetailsScreen = navigateToDetailsScreen
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