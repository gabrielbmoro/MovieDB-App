package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.model.DataOrException
import com.gabrielbmoro.programmingchallenge.domain.usecases.UnFavoriteMovieUseCase
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
    fun `should be able to remove a movie from favorite list`() {
        // arrange
        val unFavoriteUseCaseTest = getUnFavoriteUseCase()

        coEvery { repository.unFavorite("Test") }.answers { DataOrException(true, null) }

        // act
        testDispatcher.runBlockingTest {
            unFavoriteUseCaseTest.execute("Test")
        }

        // assert
        coVerify { repository.unFavorite("Test") }
    }
}