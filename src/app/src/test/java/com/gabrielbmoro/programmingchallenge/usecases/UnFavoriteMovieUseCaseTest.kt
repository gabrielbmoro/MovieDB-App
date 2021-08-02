package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class UnFavoriteMovieUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    private fun getUnFavoriteUseCase() = UnFavoriteMovieUseCase(repository)

    @Test
    fun `Remove a movie from favorite list`() {
        // arrange
        val unFavoriteUseCaseTest = getUnFavoriteUseCase()

        coEvery { repository.unFavorite("Test") }.answers { true }

        // act
        testDispatcher.runBlockingTest {
            unFavoriteUseCaseTest.execute("Test")
        }

        // assert
        coVerify { repository.unFavorite("Test") }
    }
}