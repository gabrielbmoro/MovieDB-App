package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gabrielbmoro.moviedb.domain.model.DataOrException
import com.gabrielbmoro.moviedb.repository.model.Movie
import com.gabrielbmoro.moviedb.wishlist.domain.usecases.GetFavoriteMoviesUseCase
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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
        val expected: DataOrException<List<Movie>, Exception> = DataOrException(emptyList(), null)
        coEvery { getFavoriteMoviesUseCase.invoke() }.returns(expected)
        val viewModel = WishlistViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase
        )

        // act
        viewModel.load()

        delay(500L)

        // assert
        Truth.assertThat(viewModel.uiState.value.favoriteMovies).isEqualTo(expected.data)
    }

    @Test
    fun `should be able to fetch my favorite movies - not empty list`() = runTest {
        // arrange
        val expected: DataOrException<List<Movie>, Exception> = DataOrException(
            listOf(
                Movie.mockChuckNorrisVsVandammeMovie()
            ),
            null
        )
        coEvery { getFavoriteMoviesUseCase.invoke() }.returns(expected)

        val viewModel = WishlistViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase
        )

        // act
        viewModel.load()
        delay(500L)

        // assert
        Truth.assertThat(
            viewModel.uiState.value.favoriteMovies
        ).contains(
            Movie.mockChuckNorrisVsVandammeMovie()
        )
    }
}
