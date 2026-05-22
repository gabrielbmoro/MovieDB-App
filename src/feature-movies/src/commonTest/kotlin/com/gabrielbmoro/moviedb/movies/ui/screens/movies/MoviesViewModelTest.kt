package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import MoviesHandler
import com.gabrielbmoro.moviedb.desingsystem.error.ErrorInfo
import com.gabrielbmoro.moviedb.domain.model.HttpException
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.logging.LoggerHelper
import com.gabrielbmoro.moviedb.movies.fakes.FakeLogger
import com.gabrielbmoro.moviedb.movies.fakes.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    private lateinit var repository: FakeRepository
    private lateinit var handler: MoviesHandler
    private lateinit var loggerHelper: LoggerHelper
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun before() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeRepository()
        loggerHelper = FakeLogger()
        handler = MoviesHandler(repository)
    }

    @AfterTest
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should be able to setup the view model and load movies`() = runTest {
        // arrange
        val expectedMovies = listOf(Movie.mockWhiteDragonNotFavorite())
        repository.filteredMovies = expectedMovies

        // act
        val viewModel = MoviesViewModel(
            ioDispatcher = testDispatcher,
            loggerHelper = loggerHelper,
            moviesHandler = handler,
        )
        advanceUntilIdle()

        // assert
        assertEquals(expectedMovies.size, viewModel.uiState.value.movieCardInfos.size)
        assertEquals(expectedMovies[0].title, viewModel.uiState.value.movieCardInfos[0].movieTitle)
    }

    @Test
    fun `should be able to change the filter and reload movies`() = runTest {
        // arrange
        repository.filteredMovies = emptyList()
        val viewModel = MoviesViewModel(
            ioDispatcher = testDispatcher,
            loggerHelper = loggerHelper,
            moviesHandler = handler,
        )
        advanceUntilIdle()

        val newFilter = FilterMenuItem(selected = false, type = FilterType.TopRated)
        repository.filteredMovies = listOf(Movie.mockWhiteDragonNotFavorite())

        // act
        viewModel.executeIntent(MoviesIntent.SelectFilterMenuItem(newFilter))
        advanceUntilIdle()

        // assert
        assertEquals(FilterType.TopRated, viewModel.uiState.value.selectedFilterMenu)
        assertEquals(1, viewModel.uiState.value.movieCardInfos.size)
    }

    @Test
    fun `should be able to request more movies paging`() = runTest {
        // arrange
        repository.filteredMovies = listOf(Movie.mockWhiteDragonNotFavorite())
        val viewModel = MoviesViewModel(
            ioDispatcher = testDispatcher,
            loggerHelper = loggerHelper,
            moviesHandler = handler,
        )
        advanceUntilIdle()

        val moreMovies = listOf(
            Movie.mockWhiteDragonNotFavorite().copy(id = 2L, title = "Another Movie"),
        )
        repository.filteredMovies = moreMovies

        // act
        viewModel.executeIntent(MoviesIntent.RequestMoreMovies)
        advanceUntilIdle()

        // assert
        // Resulting state should contain both the original and the new movie (paged)
        assertEquals(2, viewModel.uiState.value.movieCardInfos.size)
    }

    @Test
    fun `should be able to map HttpException to SOMETHING_WRONG_HAPPENED`() = runTest {
        // arrange
        // We create an anonymous implementation of FakeRepository that throws an exception
        val repository = FakeRepository(
            HttpException(
                statusCode = 400,
                statusDescription = "Bad Request",
                requestMethod = "GET",
                requestUrl = "https://api.themoviedb.org/3/movie/now_playing?page=0",
            ),
        )
        val handler = MoviesHandler(repository)

        // act
        val viewModel = MoviesViewModel(
            ioDispatcher = testDispatcher,
            loggerHelper = loggerHelper,
            moviesHandler = handler,
        )
        advanceUntilIdle()

        // assert
        assertEquals(ErrorInfo.SOMETHING_WRONG_HAPPENED, viewModel.uiState.value.errorInfo)
    }

    @Test
    fun `should be able to map generic Throwable to NETWORK_ERROR`() = runTest {
        // arrange
        val repository = FakeRepository(RuntimeException("Generic Error"))
        val handler = MoviesHandler(repository)

        // act
        val viewModel = MoviesViewModel(
            ioDispatcher = testDispatcher,
            loggerHelper = loggerHelper,
            moviesHandler = handler,
        )
        advanceUntilIdle()

        // assert
        assertEquals(ErrorInfo.NETWORK_ERROR, viewModel.uiState.value.errorInfo)
    }
}
