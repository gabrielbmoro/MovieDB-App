package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
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

@OptIn(ExperimentalCoroutinesApi::class)
class WishlistViewModelTest {

    private lateinit var getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
    private lateinit var favoriteMovieUseCase: FavoriteMovieUseCase
    private lateinit var isFavoriteMovieUseCase: IsFavoriteMovieUseCase
    private lateinit var resourcesProvider: ResourcesProvider

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())

        getFavoriteMoviesUseCase = mockk()
        favoriteMovieUseCase = mockk()
        isFavoriteMovieUseCase = mockk()
        resourcesProvider = mockk()
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
        coEvery { isFavoriteMovieUseCase.execute(any()) }.returns(true)
        every { resourcesProvider.getString(any()) }.returns("Chuck norris")
        coEvery { favoriteMovieUseCase.execute(any()) }.returns(Unit)

        coEvery { getFavoriteMoviesUseCase.execute(Unit) }.returns(expected)

        val viewModel = WishlistViewModel(
            getFavoriteMoviesUseCase = getFavoriteMoviesUseCase,
            favoriteMovieUseCase = favoriteMovieUseCase,
            resourcesProvider = resourcesProvider,
            isFavoriteMovieUseCase = isFavoriteMovieUseCase
        )

        // act
        viewModel.accept(WishlistUserIntent.DeleteMovie(Movie.mockChuckNorrisVsVandammeMovie()))

        // assert
        advanceUntilIdle()
        coVerify(exactly = 1) { favoriteMovieUseCase.execute(any()) }
    }
}
