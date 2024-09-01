package com.gabrielbmoro.moviedb.details.ui.screens.details

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

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {
    private lateinit var favoriteMovieUseCase: FakeFavoriteMovieUseCase
    private lateinit var isFavoriteMovieUseCase: FakeIsFavoriteMovieUseCase
    private lateinit var getMovieDetailsUseCase: FakeGetMovieDetailsUseCase

    @BeforeTest
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())

        favoriteMovieUseCase = FakeFavoriteMovieUseCase()
        isFavoriteMovieUseCase = FakeIsFavoriteMovieUseCase()
        getMovieDetailsUseCase = FakeGetMovieDetailsUseCase()
    }

    @AfterTest
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should be able to fetch movie details on launch`() =
        runTest {
            // arrange, act
            val isFavorite = false
            isFavoriteMovieUseCase.result = isFavorite
            getMovieDetailsUseCase.result = fakeMovieDetail

            val expected =
                DetailsUIState(
                    movieOverview = fakeMovieDetail.overview,
                    isFavorite = isFavorite,
                    movieVotesAverage = fakeMovieDetail.votesAverage,
                    imageUrl = fakeMovieDetail.backdropImageUrl,
                    isLoading = false,
                    moviePopularity = fakeMovieDetail.popularity,
                    movieLanguage = fakeMovieDetail.language,
                    videoId = fakeMovieDetail.videoId,
                    errorMessage = null,
                    homepage = fakeMovieDetail.homepage,
                    productionCompanies = "productionCompany1",
                    status = fakeMovieDetail.status,
                    tagLine = fakeMovieDetail.tagline,
                    genres = fakeMovieDetail.genres.toImmutableList(),
                    movieTitle = fakeMovieDetail.title
                )

            val viewModel = DetailsViewModel(
                favoriteMovieUseCase = favoriteMovieUseCase,
                isFavoriteMovieUseCase = isFavoriteMovieUseCase,
                getMovieDetailsUseCase = getMovieDetailsUseCase,
                ioDispatcher = StandardTestDispatcher()
            )
            viewModel.execute(DetailsUserIntent.LoadMovieDetails(movieId = 12))
            advanceUntilIdle()

            // assert
            val result = viewModel.uiState.value
            assertEquals(expected, result)
        }
}
