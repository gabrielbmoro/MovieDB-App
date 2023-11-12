package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gabrielbmoro.moviedb.repository.model.Movie
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WishlistViewModelTest {

    private lateinit var getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())

        getFavoriteMoviesUseCase = mockk()
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should be able to fetch my favorite movies - empty list`() = runTest {
        // arrange
        val expected: Flow<List<Movie>> = flowOf(emptyList())
        every { getFavoriteMoviesUseCase.invoke() }.returns(expected)
        val viewModel = WishlistViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase
        )

        // act
        viewModel.load().await()

        // assert
        Truth.assertThat(viewModel.uiState.value.favoriteMovies).isEmpty()
    }

    @Test
    fun `should be able to fetch my favorite movies - not empty list`() = runTest {
        // arrange
        val expected = flowOf(
            listOf(
                Movie.mockChuckNorrisVsVandammeMovie()
            )
        )
        every { getFavoriteMoviesUseCase() }.returns(expected)

        val viewModel = WishlistViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase
        )

        // act
        viewModel.load().await()

        // assert
        Truth.assertThat(
            viewModel.uiState.value.favoriteMovies
        ).contains(
            Movie.mockChuckNorrisVsVandammeMovie()
        )
    }
}
