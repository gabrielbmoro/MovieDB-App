package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.gabrielbmoro.moviedb.domain.usecases.SearchMovieUseCase
import com.gabrielbmoro.moviedb.platform.mvi.ViewModelMVI
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SearchViewModel(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModelMVI<SearchUserIntent, SearchUIState>() {

    fun setup(query: String?) {
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
