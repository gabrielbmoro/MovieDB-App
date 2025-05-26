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
    private lateinit var repository: FakeRepository

    @BeforeTest
    fun before() {
        repository = FakeRepository()
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
            repository.searchResult = emptyList()
            val viewModel = SearchViewModel(
                repository = repository,
                ioCoroutinesDispatcher = StandardTestDispatcher(),
                query = null,
            )

            // act
            viewModel.execute(SearchUserIntent.ClearSearchField)

            // assert
            advanceUntilIdle()
            assertEquals("", viewModel.uiState.value.searchQuery.text)
            assertEquals(0, viewModel.uiState.value.results?.size)
        }
}
