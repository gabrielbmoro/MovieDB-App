package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SearchScreenModelTest {

    private lateinit var searchMovieUseCase: FakeSearchUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        searchMovieUseCase = FakeSearchUseCase()
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should be able to reset the search field - empty search`() = runTest {
        // arrange
        searchMovieUseCase.searchResult = emptyList()
        val viewModel = SearchScreenModel(searchMovieUseCase)

        // act
        viewModel.accept(SearchUserIntent.ClearSearchField)

        // assert
        advanceUntilIdle()
        assertEquals("", viewModel.uiState.value.searchQuery.text)
        assertEquals(null, viewModel.uiState.value.results)
    }
}
