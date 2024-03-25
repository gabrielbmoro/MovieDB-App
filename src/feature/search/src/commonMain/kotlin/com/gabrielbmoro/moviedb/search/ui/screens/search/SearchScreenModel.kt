package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.compose.ui.text.input.TextFieldValue
import com.gabrielbmoro.moviedb.domain.usecases.SearchMovieUseCase
import com.gabrielbmoro.moviedb.platform.mvi.ScreenModelMVI

class SearchScreenModel(
    private val searchMovieUseCase: SearchMovieUseCase
) : ScreenModelMVI<SearchUserIntent, SearchUIState>() {
    override fun defaultEmptyState() = SearchUIState(TextFieldValue(""))

    override suspend fun execute(intent: SearchUserIntent): SearchUIState {
        return when (intent) {
            is SearchUserIntent.SearchBy -> {
                getState().copy(
                    results = searchMovieUseCase.execute(
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
