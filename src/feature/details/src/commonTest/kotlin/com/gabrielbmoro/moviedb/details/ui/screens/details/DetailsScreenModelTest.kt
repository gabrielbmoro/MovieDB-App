package com.gabrielbmoro.moviedb.details.ui.screens.details

import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
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
class DetailsScreenModelTest {

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
    fun `should be able to fetch movie details on launch`() = runTest {
        // arrange, act
        val isFavorite = false
        isFavoriteMovieUseCase.result = isFavorite
        val adult = false
        val budget = 12000
        val genres = listOf("genre1")
        val homePage = "homePage"
        val imdbId = "imdbId"
        val status = "status"
        val tagLine = "tagline"
        val productionCompanies = listOf("productionCompany1")
        val videoId = "videoId"

        val expected = DetailsUIState(
            movieOverview = fakeMovie.overview,
            isFavorite = isFavorite,
            movieVotesAverage = fakeMovie.votesAverage,
            imageUrl = fakeMovie.backdropImageUrl,
            isLoading = false,
            moviePopularity = fakeMovie.popularity,
            movieLanguage = fakeMovie.language,
            videoId = videoId,
            errorMessage = null,
            homepage = homePage,
            productionCompanies = "productionCompany1",
            status = status,
            tagLine = tagLine,
            genres = genres,
            movieTitle = fakeMovie.title
        )

        getMovieDetailsUseCase.result = MovieDetail(
            adult = adult,
            budget = budget,
            genres = genres,
            homepage = homePage,
            imdbId = imdbId,
            status = status,
            tagline = tagLine,
            productionCompanies = productionCompanies,
            videoId = videoId,
        )
        val viewModel = DetailsScreenScreenModel(
            movie = fakeMovie,
            favoriteMovieUseCase = favoriteMovieUseCase,
            isFavoriteMovieUseCase = isFavoriteMovieUseCase,
            getMovieDetailsUseCase = getMovieDetailsUseCase
        )

        // assert
        advanceUntilIdle()
        val result = viewModel.uiState.value
        assertEquals(expected, result)
    }
}