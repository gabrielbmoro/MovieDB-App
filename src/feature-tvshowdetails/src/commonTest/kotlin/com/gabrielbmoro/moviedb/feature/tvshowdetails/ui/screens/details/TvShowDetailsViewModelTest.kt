package com.gabrielbmoro.moviedb.feature.tvshowdetails.ui.screens.details

import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class TvShowDetailsViewModelTest {
    private lateinit var getTvShowDetailsUseCase: FakeGetTvShowDetailsUseCase
    private lateinit var loggerHelper: FakeLoggerHelper

    @BeforeTest
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())
        getTvShowDetailsUseCase = FakeGetTvShowDetailsUseCase()
        loggerHelper = FakeLoggerHelper()
    }

    @AfterTest
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load details and map all fields to UI state`() =
        runTest {
            val expected =
                TvShowDetailsUIState(
                    tvShowName = fakeTvShowDetail.name,
                    isLoading = false,
                    votesAverage = fakeTvShowDetail.votesAverage,
                    language = fakeTvShowDetail.originalLanguage,
                    popularity = fakeTvShowDetail.popularity,
                    overview = fakeTvShowDetail.overview,
                    imageUrl = fakeTvShowDetail.backdropImageUrl,
                    tagline = fakeTvShowDetail.tagline,
                    genres = fakeTvShowDetail.genres.toImmutableList(),
                    status = fakeTvShowDetail.status,
                    firstAirDate = fakeTvShowDetail.firstAirDate,
                    lastAirDate = fakeTvShowDetail.lastAirDate,
                    networks = fakeTvShowDetail.networks.toImmutableList(),
                    createdBy = fakeTvShowDetail.createdBy.toImmutableList(),
                    productionCompanies = fakeTvShowDetail.productionCompanies.toImmutableList(),
                    homepage = fakeTvShowDetail.homepage,
                    numberOfSeasons = fakeTvShowDetail.numberOfSeasons,
                    numberOfEpisodes = fakeTvShowDetail.numberOfEpisodes,
                    videoId = fakeTvShowDetail.videoId,
                    showVideo = true,
                    errorMessage = null,
                )

            val viewModel =
                TvShowDetailsViewModel(
                    getTvShowDetailsUseCase = getTvShowDetailsUseCase,
                    ioDispatcher = StandardTestDispatcher(),
                    loggerHelper = loggerHelper,
                )
            viewModel.executeIntent(TvShowDetailsUserIntent.LoadTvShowDetails(tvShowId = 123L))
            advanceUntilIdle()

            val result = viewModel.uiState.value
            assertEquals(expected, result)
        }

    @Test
    fun `should set isLoading true while fetch in progress`() =
        runTest {
            val viewModel =
                TvShowDetailsViewModel(
                    getTvShowDetailsUseCase = getTvShowDetailsUseCase,
                    ioDispatcher = StandardTestDispatcher(),
                    loggerHelper = loggerHelper,
                )
            viewModel.executeIntent(TvShowDetailsUserIntent.LoadTvShowDetails(tvShowId = 123L))

            val result = viewModel.uiState.value
            assertTrue(result.isLoading)
        }

    @Test
    fun `should set errorMessage and isLoading false on fetch failure`() =
        runTest {
            getTvShowDetailsUseCase.result = Result.failure(RuntimeException("Network error"))

            val viewModel =
                TvShowDetailsViewModel(
                    getTvShowDetailsUseCase = getTvShowDetailsUseCase,
                    ioDispatcher = StandardTestDispatcher(),
                    loggerHelper = loggerHelper,
                )
            viewModel.executeIntent(TvShowDetailsUserIntent.LoadTvShowDetails(tvShowId = 123L))
            advanceUntilIdle()

            val result = viewModel.uiState.value
            assertFalse(result.isLoading)
            assertNotNull(result.errorMessage)
        }

    @Test
    fun `should hide video on HideVideo intent`() =
        runTest {
            val viewModel =
                TvShowDetailsViewModel(
                    getTvShowDetailsUseCase = getTvShowDetailsUseCase,
                    ioDispatcher = StandardTestDispatcher(),
                    loggerHelper = loggerHelper,
                )
            viewModel.executeIntent(TvShowDetailsUserIntent.LoadTvShowDetails(tvShowId = 123L))
            advanceUntilIdle()
            assertEquals(true, viewModel.uiState.value.showVideo)

            viewModel.executeIntent(TvShowDetailsUserIntent.HideVideo)
            advanceUntilIdle()
            assertEquals(false, viewModel.uiState.value.showVideo)
        }

    @Test
    fun `should handle null optional fields without crash`() =
        runTest {
            getTvShowDetailsUseCase.result = Result.success(fakeTvShowDetailEmptyOptionals)

            val viewModel =
                TvShowDetailsViewModel(
                    getTvShowDetailsUseCase = getTvShowDetailsUseCase,
                    ioDispatcher = StandardTestDispatcher(),
                    loggerHelper = loggerHelper,
                )
            viewModel.executeIntent(TvShowDetailsUserIntent.LoadTvShowDetails(tvShowId = 456L))
            advanceUntilIdle()

            val result = viewModel.uiState.value
            assertFalse(result.isLoading)
            assertEquals(null, result.errorMessage)
            assertEquals(null, result.tagline)
            assertEquals(null, result.homepage)
        }
}
