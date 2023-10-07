package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUIState(""))

    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _uiState.value
    )

    fun onSearchQueryChanged(searchQuery: String) {
        _uiState.update { it.copy(searchQuery = searchQuery) }
    }

    fun onClearSearchQuery() {
        _uiState.update { it.copy(searchQuery = "") }
    }
}