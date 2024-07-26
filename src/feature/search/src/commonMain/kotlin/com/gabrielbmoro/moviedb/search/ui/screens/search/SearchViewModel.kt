package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.usecases.SearchMovieUseCase
import com.gabrielbmoro.moviedb.platform.mvi.ViewModelMVI
import kotlinx.coroutines.launch

class SearchViewModel(
    private val query: String? = null,
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModelMVI<SearchUserIntent, SearchUIState>() {
    init {
        query?.let {
            viewModelScope.launch {
                accept(SearchUserIntent.SearchInputFieldChanged(TextFieldValue(it)))
            }
        }
    }
    override fun defaultEmptyState() = SearchUIState(TextFieldValue(""))

    override suspend fun execute(intent: SearchUserIntent): SearchUIState {
        return when (intent) {
            is SearchUserIntent.SearchBy -> {
                getState().copy(
                    results =
                    searchMovieUseCase.execute(
                        SearchMovieUseCase.Params(
                            query = intent.query.text
                        )
                    )
                )
            }

            is SearchUserIntent.ClearSearchField -> {
                getState().copy(
                    searchQuery = TextFieldValue("")
                )
            }

            is SearchUserIntent.SearchInputFieldChanged -> {
                getState().copy(
                    searchQuery = intent.query
                )
            }
        }
    }
}
