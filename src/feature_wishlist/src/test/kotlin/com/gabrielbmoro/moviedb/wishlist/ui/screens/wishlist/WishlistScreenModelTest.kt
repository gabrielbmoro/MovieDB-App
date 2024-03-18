package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gabrielbmoro.moviedb.domain.entities.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class WishlistScreenModelTest {

    private lateinit var getFavoriteMoviesUseCase: FakeGetFavoriteMoviesUseCase
    private lateinit var favoriteMovieUseCase: FakeFavoriteMovieUseCase
    private lateinit var isFavoriteMovieUseCase: FakeIsFavoriteMovieUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())

        getFavoriteMoviesUseCase = FakeGetFavoriteMoviesUseCase()
        favoriteMovieUseCase = FakeFavoriteMovieUseCase()
        isFavoriteMovieUseCase = FakeIsFavoriteMovieUseCase()
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should be able to process delete movie intent`() = runTest {
        // arrange
        val expected = listOf(
                Movie.mockChuckNorrisVsVandammeMovie()
            )
        isFavoriteMovieUseCase.result = true
        getFavoriteMoviesUseCase.result = expected

        val viewModel = WishlistScreenModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            favoriteMovieUseCase = favoriteMovieUseCase,
            isFavoriteMovieUseCase = isFavoriteMovieUseCase
        )

        // act
        viewModel.accept(WishlistUserIntent.DeleteMovie(Movie.mockChuckNorrisVsVandammeMovie()))

        // assert
        advanceUntilIdle()
        assertEquals(1, favoriteMovieUseCase.timesCalled)
    }
}
