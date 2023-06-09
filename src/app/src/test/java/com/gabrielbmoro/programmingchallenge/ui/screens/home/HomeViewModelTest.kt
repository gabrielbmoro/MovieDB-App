package com.gabrielbmoro.programmingchallenge.ui.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.gabrielbmoro.programmingchallenge.MainDispatcherRule
import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.SearchType
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.emptyFlow
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
        // arrange, act
        val viewModel = HomeViewModel(
            MovieListType.FAVORITE,
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        // assert
        Truth.assertThat(viewModel.uiState.value.selectedMovieType)
            .isEqualTo(MovieListType.FAVORITE)

    }

    @Test
    fun `should be able to select top rated movies`() {
        // arrange
        every { getTopRatedMoviesUseCase.invoke() }.returns(emptyFlow())
        every { getPopularMoviesUseCase.invoke() }.returns(emptyFlow())

        // act
        val viewModel = HomeViewModel(
            MovieListType.TOP_RATED,
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        // assert
        Truth.assertThat(viewModel.uiState.value.selectedMovieType)
            .isEqualTo(MovieListType.TOP_RATED)

    }

    @Test
    fun `should be able to select popular movies`() {
        // arrange
        every { getTopRatedMoviesUseCase.invoke() }.returns(emptyFlow())
        every { getPopularMoviesUseCase.invoke() }.returns(emptyFlow())

        // act
        val viewModel = HomeViewModel(
            MovieListType.POPULAR,
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )


        // assert
        Truth.assertThat(viewModel.uiState.value.selectedMovieType)
            .isEqualTo(MovieListType.POPULAR)
    }

    @Test
    fun `should be able to fetch my favorite movies - empty list`() {
        // arrange
        val expected: DataOrException<List<Movie>, Exception> = DataOrException(emptyList(), null)
        coEvery { getFavoriteMoviesUseCase.invoke() }.returns(expected)

        // act
        val viewModel = HomeViewModel(
            MovieListType.FAVORITE,
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            delay(500L)

            // assert
            Truth.assertThat(viewModel.uiState.value.favoriteMovies).isEqualTo(expected.data)
        }
    }


    @Test
    fun `should be able to fetch my favorite movies - not empty list`() {
        // arrange
        val expected: DataOrException<List<Movie>, Exception> = DataOrException(
            listOf(
                Movie.mockChuckNorrisVsVandammeMovie()
            ), null
        )
        coEvery { getFavoriteMoviesUseCase.invoke() }.returns(expected)

        // act
        val viewModel = HomeViewModel(
            MovieListType.FAVORITE,
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            delay(500L)

            // assert
            Truth.assertThat(
                viewModel.uiState.value.favoriteMovies
            ).contains(
                Movie.mockChuckNorrisVsVandammeMovie()
            )
        }
    }


    @Test
    fun `should be able to search for top rated movies`() {
        // arrange
        every { getTopRatedMoviesUseCase.invoke() }.returns(emptyFlow())
        every { getPopularMoviesUseCase.invoke() }.returns(emptyFlow())

        val viewModel = HomeViewModel(
            MovieListType.POPULAR,
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.onSearchBy(SearchType.TOP_RATED)

            delay(100L)

            // assert
            Truth.assertThat(viewModel.uiState.value.selectedMovieType)
                .isEqualTo(MovieListType.TOP_RATED)
        }
    }

    @Test
    fun `should be able to search for popular movies`() {
        // arrange
        every { getTopRatedMoviesUseCase.invoke() }.returns(emptyFlow())
        every { getPopularMoviesUseCase.invoke() }.returns(emptyFlow())

        val viewModel = HomeViewModel(
            MovieListType.TOP_RATED,
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            getPopularMoviesUseCase = getPopularMoviesUseCase,
            getTopRatedMoviesUseCase = getTopRatedMoviesUseCase
        )

        runTest {
            // act
            viewModel.onSearchBy(SearchType.POPULAR)

            delay(100L)

            // assert
            Truth.assertThat(viewModel.uiState.value.selectedMovieType)
                .isEqualTo(MovieListType.POPULAR)
        }
    }
}