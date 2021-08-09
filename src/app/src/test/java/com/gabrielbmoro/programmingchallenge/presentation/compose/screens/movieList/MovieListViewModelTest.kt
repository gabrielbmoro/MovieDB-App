package com.gabrielbmoro.programmingchallenge.presentation.compose.screens.movieList

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.screens.movieList.MovieListViewModel
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.repository.entities.Page
import com.gabrielbmoro.programmingchallenge.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetTopRatedMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val application: Application = mockk()
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase = mockk()
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase = mockk()
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk()

    private fun getViewModel(): MovieListViewModel {
        return MovieListViewModel(
            application,
            getFavoriteMoviesUseCase,
            getTopRatedMoviesUseCase,
            getPopularMoviesUseCase
        )
    }

    @Test
    fun `should be able to fetch favorite movies`() {
        // arrange
        val viewModelTest = getViewModel()
        viewModelTest.setup(MovieListType.Favorite)

        // act
        viewModelTest.refresh()

        // assert
        coVerify { getFavoriteMoviesUseCase.execute() }
    }

    @Test
    fun `should be able to fetch top rated movies`() {
        // arrange
        val viewModelTest = getViewModel()
        viewModelTest.setup(MovieListType.TopRated)

        // act
        viewModelTest.refresh()

        // assert
        coVerify { getTopRatedMoviesUseCase.execute(1) }
    }

    @Test
    fun `should be able to fetch popular movies`() {
        // arrange
        val viewModelTest = getViewModel()
        viewModelTest.setup(MovieListType.TopRated)

        // act
        viewModelTest.refresh()

        // assert
        coVerify { getTopRatedMoviesUseCase.execute(1) }
    }

    @Test
    fun `should be able to request a next page of movies`() {
        // arrange
        coEvery { getTopRatedMoviesUseCase.execute(1) }.coAnswers {
            Page(
                movies = emptyList(),
                pageNumber = 1,
                totalPages = 2
            )
        }
        val viewModelTest = getViewModel()
        viewModelTest.setup(MovieListType.TopRated)

        // act
        viewModelTest.refresh()
        viewModelTest.requestMore()

        // assert
        coVerify { getTopRatedMoviesUseCase.execute(2) }
    }

    @Test
    fun `should not be able to request the next page when it exceeds the number of pages`() {
        // arrange
        coEvery { getTopRatedMoviesUseCase.execute(1) }.coAnswers {
            Page(
                movies = emptyList(),
                pageNumber = 1,
                totalPages = 1
            )
        }
        val viewModelTest = getViewModel()
        viewModelTest.setup(MovieListType.TopRated)

        // act
        viewModelTest.refresh()
        viewModelTest.requestMore()

        // assert
        coVerify(exactly = 0) { getTopRatedMoviesUseCase.execute(2) }
    }
}