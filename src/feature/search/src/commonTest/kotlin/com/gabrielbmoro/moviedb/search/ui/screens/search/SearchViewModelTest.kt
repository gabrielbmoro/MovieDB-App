package com.gabrielbmoro.moviedb.search.ui.screens.search

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private lateinit var searchMovieUseCase: FakeSearchUseCase

    @BeforeTest
    fun before() {
        searchMovieUseCase = FakeSearchUseCase()
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should be able to reset the search field - empty search`() =
        runTest {
            // arrange
            searchMovieUseCase.searchResult = emptyList()
            val viewModel = SearchViewModel(
                searchMovieUseCase = searchMovieUseCase,
                ioCoroutinesDispatcher = StandardTestDispatcher(),
                query = null
            )

            // act
            viewModel.execute(SearchUserIntent.ClearSearchField)

            // assert
            advanceUntilIdle()
            assertEquals("", viewModel.uiState.value.searchQuery.text)
            assertEquals(0, viewModel.uiState.value.results?.size)
        }
}
