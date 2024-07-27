package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.usecases.SearchMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory

@Factory
class SearchViewModel(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    fun setup(query: String?) {
        query?.let {
            viewModelScope.launch {
                execute(SearchUserIntent.SearchInputFieldChanged(TextFieldValue(it)))
            }
        }
    }

    private fun defaultEmptyState() = SearchUIState(TextFieldValue(""))

    fun execute(intent: SearchUserIntent) {
        when (intent) {
            is SearchUserIntent.SearchBy -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            results =
                            searchMovieUseCase.execute(
                                SearchMovieUseCase.Params(
                                    query = intent.query.text
                                )
                            )
                        )
                    }
                }
            }

            is SearchUserIntent.ClearSearchField -> {
                _uiState.update {
                    it.copy(
                        searchQuery = TextFieldValue("")
                    )
                }
            }

            is SearchUserIntent.SearchInputFieldChanged -> {
                _uiState.update {
                    it.copy(
                        searchQuery = intent.query
                    )
                }
            }
        }
    }
}
