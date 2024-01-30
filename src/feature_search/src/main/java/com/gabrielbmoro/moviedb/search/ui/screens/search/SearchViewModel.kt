package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.search.domain.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUIState(TextFieldValue("")))

    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    fun onQueryChanged(searchQuery: TextFieldValue) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }

    fun onClearSearchQuery() {
        _uiState.update {
            it.copy(
                searchQuery = TextFieldValue(text = ""),
                results = null
            )
        }
    }

    fun onSearchBy(query: String) {
        viewModelScope.launch {
            searchMovieUseCase(query).collect { movies ->
                _uiState.update {
                    it.copy(results = movies)
                }
            }
        }
    }
}
