package com.gabrielbmoro.programmingchallenge.presentation.compose.screens.movieList

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.screens.movieList.MovieListViewModel
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetTopRatedMoviesUseCase
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
}