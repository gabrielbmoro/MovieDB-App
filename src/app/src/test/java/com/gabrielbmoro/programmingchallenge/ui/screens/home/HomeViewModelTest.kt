package com.gabrielbmoro.programmingchallenge.ui.screens.home

import com.gabrielbmoro.programmingchallenge.MainDispatcherRule
import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetTopRatedMoviesUseCase
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun before() {
        getFavoriteMoviesUseCase = mockk()
        getTopRatedMoviesUseCase = mockk()
        getPopularMoviesUseCase = mockk()
    }


    @Test
    fun `should be able to fetch my favorite movies, not calling getPopularUseCase`() {
        val expected: DataOrException<List<Movie>, Exception> = DataOrException(emptyList(), null)
        coEvery { getFavoriteMoviesUseCase.invoke() }.returns(expected)

        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        // act
        viewModel.setup(MovieListType.Favorite)

        // assert
        coVerify(exactly = 0) { getPopularMoviesUseCase.invoke(1) }
    }

    @Test
    fun `should be able to fetch my favorite movies, not calling getTopRatedUseCase`() {
        val expected: DataOrException<List<Movie>, Exception> = DataOrException(emptyList(), null)
        coEvery { getFavoriteMoviesUseCase.invoke() }.returns(expected)

        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        // act
        viewModel.setup(MovieListType.Favorite)

        // assert
        coVerify(exactly = 0) { getTopRatedMoviesUseCase.invoke(1) }
    }

    @Test
    fun `should be able to fetch my favorite movies, calling getFavoritesUseCase - empty list`() {
        val expected: DataOrException<List<Movie>, Exception> = DataOrException(emptyList(), null)
        coEvery { getFavoriteMoviesUseCase.invoke() }.returns(expected)

        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        // act
        viewModel.setup(MovieListType.Favorite)

        // assert
        runTest {
            Truth.assertThat(getFavoriteMoviesUseCase.invoke()).isEqualTo(expected)
        }
    }
}