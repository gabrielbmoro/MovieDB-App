package com.gabrielbmoro.programmingchallenge.ui.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gabrielbmoro.programmingchallenge.MainDispatcherRule
import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetTopRatedMoviesUseCase
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
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

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        getFavoriteMoviesUseCase = mockk()
        getTopRatedMoviesUseCase = mockk()
        getPopularMoviesUseCase = mockk()
    }

    @Test
    fun `should be able to select favorite movies`() {
        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.setup(MovieListType.FAVORITE)

            // assert
            Truth.assertThat(viewModel.uiState.value.selectedMovieListType)
                .isEqualTo(MovieListType.FAVORITE)
        }
    }

    @Test
    fun `should be able to select top rated movies`() {
        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.setup(MovieListType.TOP_RATED)

            // assert
            Truth.assertThat(viewModel.uiState.value.selectedMovieListType)
                .isEqualTo(MovieListType.TOP_RATED)
        }
    }

    @Test
    fun `should be able to select popular movies`() {
        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.setup(MovieListType.POPULAR)

            // assert
            Truth.assertThat(viewModel.uiState.value.selectedMovieListType)
                .isEqualTo(MovieListType.POPULAR)
        }
    }

    @Test
    fun `should be able to fetch my favorite movies - empty list`() {
        val expected: DataOrException<List<Movie>, Exception> = DataOrException(emptyList(), null)
        coEvery() { getFavoriteMoviesUseCase.invoke() }.returns(expected)

        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.setup(MovieListType.FAVORITE)
            // assert
            val job = async {
                Truth.assertThat(viewModel.uiState.value.movies).isEqualTo(expected.data)
            }
            job.await()
        }
    }

    @Test
    fun `should be able to fetch my favorite movies - not empty list`() {
        val expected: DataOrException<List<Movie>, Exception> = DataOrException(
            listOf(
                Movie.mockChuckNorrisVsVandammeMovie()
            ), null
        )
        coEvery() { getFavoriteMoviesUseCase.invoke() }.returns(expected)

        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.setup(MovieListType.FAVORITE)

            // assert
            val job = async {
                Truth.assertThat(
                    viewModel.uiState.value.movies
                ).contains(
                    Movie.mockChuckNorrisVsVandammeMovie()
                )
            }
            job.await()
        }
    }

    @Test
    fun `should be able to fetch top rated movies - empty list`() {
        val expected: DataOrException<Page, Exception> =
            DataOrException(Page(emptyList(), 1, 1), null)
        coEvery { getTopRatedMoviesUseCase.invoke(1) }.returns(expected)

        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.setup(MovieListType.TOP_RATED)
            // assert
            val job = async {
                Truth.assertThat(viewModel.uiState.value.movies).isEqualTo(expected.data?.movies)
            }
            job.await()
        }
    }

    @Test
    fun `should be able to fetch top rated movies - not empty list`() {
        val expected: DataOrException<Page, Exception> = DataOrException(
            Page(
                listOf(
                    Movie.mockChuckNorrisVsVandammeMovie()
                ), 1, 1
            ), null
        )
        coEvery { getTopRatedMoviesUseCase.invoke(1) }.returns(expected)

        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.setup(MovieListType.TOP_RATED)

            // assert
            val job = async {
                Truth.assertThat(
                    viewModel.uiState.value.movies
                ).contains(
                    Movie.mockChuckNorrisVsVandammeMovie()
                )
            }
            job.await()
        }
    }

    @Test
    fun `should be able to fetch popular movies - empty list`() {
        val expected: DataOrException<Page, Exception> =
            DataOrException(Page(emptyList(), 1, 1), null)
        coEvery { getPopularMoviesUseCase.invoke(1) }.returns(expected)

        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.setup(MovieListType.POPULAR)
            // assert
            val job = async {
                Truth.assertThat(viewModel.uiState.value.movies).isEqualTo(expected.data?.movies)
            }
            job.await()
        }
    }

    @Test
    fun `should be able to fetch popular movies - not empty list`() {
        val expected: DataOrException<Page, Exception> = DataOrException(
            Page(
                listOf(
                    Movie.mockChuckNorrisVsVandammeMovie()
                ), 1, 1
            ), null
        )
        coEvery { getPopularMoviesUseCase.invoke(1) }.returns(expected)

        // arrange
        val viewModel = HomeViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.setup(MovieListType.POPULAR)

            // assert
            val job = async {
                Truth.assertThat(
                    viewModel.uiState.value.movies
                ).contains(
                    Movie.mockChuckNorrisVsVandammeMovie()
                )
            }
            job.await()
        }
    }
}