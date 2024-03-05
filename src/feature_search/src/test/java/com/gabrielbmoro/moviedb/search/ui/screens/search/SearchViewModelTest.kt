package com.gabrielbmoro.moviedb.search.ui.screens.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gabrielbmoro.moviedb.domain.usecases.SearchMovieUseCase
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
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

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var searchMovieUseCase: SearchMovieUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        searchMovieUseCase = mockk()
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should be able to reset the search field - empty search`() = runTest {
        // arrange
        coEvery { searchMovieUseCase.execute(any()) }.returns(emptyList())
        val viewModel = SearchViewModel(searchMovieUseCase)

        // act
        viewModel.accept(SearchUserIntent.ClearSearchField)

        // assert
        advanceUntilIdle()
        Truth.assertThat(viewModel.uiState.value.searchQuery.text).isEmpty()
        Truth.assertThat(viewModel.uiState.value.results).isNull()
    }
}
