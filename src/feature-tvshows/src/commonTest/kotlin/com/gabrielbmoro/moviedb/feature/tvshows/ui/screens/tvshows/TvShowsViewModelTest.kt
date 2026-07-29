package com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows

import com.gabrielbmoro.moviedb.desingsystem.error.ErrorInfo
import com.gabrielbmoro.moviedb.domain.model.HttpException
import com.gabrielbmoro.moviedb.domain.model.TvShow
import com.gabrielbmoro.moviedb.feature.tvshows.components.TvShowsHandler
import com.gabrielbmoro.moviedb.feature.tvshows.fakes.FakeLogger
import com.gabrielbmoro.moviedb.feature.tvshows.fakes.FakeTvShowsRepository
import com.gabrielbmoro.moviedb.platform.logging.LoggerHelper
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
class TvShowsViewModelTest {

    private lateinit var repository: FakeTvShowsRepository
    private lateinit var handler: TvShowsHandler
    private lateinit var loggerHelper: LoggerHelper
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun before() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeTvShowsRepository()
        loggerHelper = FakeLogger()
        handler = TvShowsHandler(repository)
    }

    @AfterTest
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should be able to setup the view model and load tv shows`() = runTest {
        // arrange
        val expectedTvShows = listOf(TvShow.mockBreakingBad())
        repository.filteredTvShows = expectedTvShows

        // act
        val viewModel = TvShowsViewModel(
            ioDispatcher = testDispatcher,
            loggerHelper = loggerHelper,
            tvShowsHandler = handler,
        )
        advanceUntilIdle()

        // assert
        assertEquals(expectedTvShows.size, viewModel.uiState.value.tvShowCardInfos.size)
        assertEquals(
            expectedTvShows[0].name,
            viewModel.uiState.value.tvShowCardInfos[0].tvShowTitle,
        )
    }

    @Test
    fun `should be able to change the filter and reload tv shows`() = runTest {
        // arrange
        repository.filteredTvShows = emptyList()
        val viewModel = TvShowsViewModel(
            ioDispatcher = testDispatcher,
            loggerHelper = loggerHelper,
            tvShowsHandler = handler,
        )
        advanceUntilIdle()

        val newFilter = FilterMenuItem(selected = false, type = TvShowFilterType.TopRated)
        repository.filteredTvShows = listOf(TvShow.mockBreakingBad())

        // act
        viewModel.executeIntent(TvShowsIntent.SelectFilterMenuItem(newFilter))
        advanceUntilIdle()

        // assert
        assertEquals(TvShowFilterType.TopRated, viewModel.uiState.value.selectedFilterMenu)
        assertEquals(1, viewModel.uiState.value.tvShowCardInfos.size)
    }

    @Test
    fun `should be able to request more tv shows paging`() = runTest {
        // arrange
        repository.filteredTvShows = listOf(TvShow.mockBreakingBad())
        val viewModel = TvShowsViewModel(
            ioDispatcher = testDispatcher,
            loggerHelper = loggerHelper,
            tvShowsHandler = handler,
        )
        advanceUntilIdle()

        val moreTvShows = listOf(
            TvShow.mockBreakingBad().copy(id = 2L, name = "Another TV Show"),
        )
        repository.filteredTvShows = moreTvShows

        // act
        viewModel.executeIntent(TvShowsIntent.RequestMoreTvShows)
        advanceUntilIdle()

        // assert
        assertEquals(2, viewModel.uiState.value.tvShowCardInfos.size)
    }

    @Test
    fun `should be able to map HttpException to SOMETHING_WRONG_HAPPENED`() = runTest {
        // arrange
        val repository = FakeTvShowsRepository(
            HttpException(
                statusCode = 400,
                statusDescription = "Bad Request",
                requestMethod = "GET",
                requestUrl = "https://api.themoviedb.org/3/tv/popular?page=0",
            ),
        )
        val handler = TvShowsHandler(repository)

        // act
        val viewModel = TvShowsViewModel(
            ioDispatcher = testDispatcher,
            loggerHelper = loggerHelper,
            tvShowsHandler = handler,
        )
        advanceUntilIdle()

        // assert
        assertEquals(ErrorInfo.SOMETHING_WRONG_HAPPENED, viewModel.uiState.value.errorInfo)
    }

    @Test
    fun `should be able to map generic Throwable to NETWORK_ERROR`() = runTest {
        // arrange
        val repository = FakeTvShowsRepository(RuntimeException("Generic Error"))
        val handler = TvShowsHandler(repository)

        // act
        val viewModel = TvShowsViewModel(
            ioDispatcher = testDispatcher,
            loggerHelper = loggerHelper,
            tvShowsHandler = handler,
        )
        advanceUntilIdle()

        // assert
        assertEquals(ErrorInfo.NETWORK_ERROR, viewModel.uiState.value.errorInfo)
    }
}
