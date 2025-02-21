package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.wishlist.ui.widgets.MovieCardInfo
import kotlinx.collections.immutable.persistentListOf
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class WishlistViewModelTest {
    private lateinit var getFavoriteMoviesUseCase: FakeGetFavoriteMoviesUseCase
    private lateinit var favoriteMovieUseCase: FakeFavoriteMovieUseCase
    private lateinit var isFavoriteMovieUseCase: FakeIsFavoriteMovieUseCase
    private val mockChuckNorrisVsVandammeMovieCardInfo =
        Movie.mockChuckNorrisVsVandammeMovie().let {
            MovieCardInfo(
                id = it.id,
                title = it.title,
                posterImageUrl = it.posterImageUrl,
                votesAverage = it.votesAverage,
                overview = it.overview,
            )
        }

    @BeforeTest
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())

        getFavoriteMoviesUseCase = FakeGetFavoriteMoviesUseCase()
        favoriteMovieUseCase = FakeFavoriteMovieUseCase()
        isFavoriteMovieUseCase = FakeIsFavoriteMovieUseCase()
    }

    @AfterTest
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should be able to process prepare to delete movie intent`() =
        runTest {
            // arrange
            val expected =
                listOf(
                    Movie.mockChuckNorrisVsVandammeMovie(),
                )

            isFavoriteMovieUseCase.result = true
            getFavoriteMoviesUseCase.result = expected

            val viewModel =
                WishlistViewModel(
                    getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
                    favoriteMovieUseCase = favoriteMovieUseCase,
                    isFavoriteMovieUseCase = isFavoriteMovieUseCase,
                    ioCoroutinesDispatcher = StandardTestDispatcher(),
                )

            // act
            viewModel.execute(
                WishlistUserIntent.PrepareToDeleteMovie(
                    mockChuckNorrisVsVandammeMovieCardInfo,
                ),
            )
            advanceUntilIdle()

            // assert
            assertTrue(viewModel.uiState.value.isDeleteAlertDialogVisible)
        }

    @Test
    fun `should be able to load all favorite movies`() =
        runTest {
            // arrange
            val expected =
                persistentListOf(
                    mockChuckNorrisVsVandammeMovieCardInfo,
                )
            isFavoriteMovieUseCase.result = true
            getFavoriteMoviesUseCase.result = listOf(Movie.mockChuckNorrisVsVandammeMovie())

            val viewModel =
                WishlistViewModel(
                    getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
                    favoriteMovieUseCase = favoriteMovieUseCase,
                    isFavoriteMovieUseCase = isFavoriteMovieUseCase,
                    ioCoroutinesDispatcher = StandardTestDispatcher(),
                )

            // act
            viewModel.execute(WishlistUserIntent.LoadMovies)
            advanceUntilIdle()

            // assert
            assertEquals(expected, viewModel.uiState.value.favoriteMovies)
        }

    @Test
    fun `should be able to process to delete movie intent`() =
        runTest {
            // arrange
            val expected =
                listOf(
                    Movie.mockChuckNorrisVsVandammeMovie(),
                )
            isFavoriteMovieUseCase.result = true
            getFavoriteMoviesUseCase.result = expected

            val viewModel =
                WishlistViewModel(
                    getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
                    favoriteMovieUseCase = favoriteMovieUseCase,
                    isFavoriteMovieUseCase = isFavoriteMovieUseCase,
                    ioCoroutinesDispatcher = StandardTestDispatcher(),
                )
            viewModel.execute(
                WishlistUserIntent.PrepareToDeleteMovie(
                    mockChuckNorrisVsVandammeMovieCardInfo,
                ),
            )
            advanceUntilIdle()

            // act
            viewModel.execute(WishlistUserIntent.DeleteMovie)
            advanceUntilIdle()

            // assert
            assertEquals(1, favoriteMovieUseCase.timesCalled)
        }
}
