package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabrielbmoro.moviedb.repository.model.Movie
import com.gabrielbmoro.moviedb.search.ui.widgets.MoviesResult
import com.gabrielbmoro.moviedb.search.ui.widgets.SearchInputText

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel(), navigateToDetailsScreen: ((Movie) -> Unit)) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
    ) {
        SearchInputText(
            currentValue = uiState.value.searchQuery,
            onValueChanged = {
                viewModel.onSearchQueryChanged(it)
            },
            onClearText = {
                viewModel.onClearSearchQuery()
            },
            modifier = Modifier.fillMaxWidth()
        )

        uiState.value.results?.let { movies ->
            MoviesResult(
                movies = movies,
                modifier = Modifier.fillMaxWidth(),
                navigateToDetailsScreen = navigateToDetailsScreen
            )
        }
    }
}