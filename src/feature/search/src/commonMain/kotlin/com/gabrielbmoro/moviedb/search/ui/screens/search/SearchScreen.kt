@file:Suppress("LongMethod")

package com.gabrielbmoro.moviedb.search.ui.screens.search

import CustomAppToolbar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gabrielbmoro.moviedb.platform.navigation.navigateToDetails
import com.gabrielbmoro.moviedb.search.ui.widgets.MoviesResult
import com.gabrielbmoro.moviedb.search.ui.widgets.SearchInputText
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

private const val DELAY_IN_MILLIS = 500L

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SearchScreen(
    query: String?,
    viewModel: SearchViewModel = koinViewModel(),
    navigator: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()

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
                            viewModel.execute(SearchUserIntent.SearchInputFieldChanged(it))
                        },
                        onSearchBy = {
                            viewModel.execute(SearchUserIntent.SearchBy(it))
                        },
                        onClearText = {
                            viewModel.execute(SearchUserIntent.ClearSearchField)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        focusRequester = focusRequester
                    )
                },
                backEvent = navigator::popBackStack
            )
        }
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ) {
            if (uiState.value.results != null) {
                MoviesResult(
                    movies = uiState.value.results!!,
                    modifier = Modifier.fillMaxWidth(),
                    navigateToDetailsScreen = { movie ->
                        navigator.navigateToDetails(movie.id)
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
                delay(DELAY_IN_MILLIS)
                keyboard?.show()
            }
        }
    )

    LaunchedEffect(query) {
        query?.let {
            viewModel.setup(
                query = it
            )
        }
    }
}
