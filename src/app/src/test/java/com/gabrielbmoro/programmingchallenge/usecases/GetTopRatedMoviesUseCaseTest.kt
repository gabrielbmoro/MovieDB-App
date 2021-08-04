package com.gabrielbmoro.programmingchallenge.usecases

import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.entities.Page
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import com.gabrielbmoro.programmingchallenge.usecases.mappers.PageMapper
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
class GetTopRatedMoviesUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val mapper: PageMapper = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    private fun getTopRatedMovieUseCaseTest() = GetTopRatedMoviesUseCase(repository, mapper)

    @Test
    fun `should be able to get popular movies`() {
        // arrange
        val topRatedUseCaseTest = getTopRatedMovieUseCaseTest()
        coEvery {
            mapper.map(any())
        }.answers {
            Page(
                movies = listOf(),
                totalPages = 2,
                pageNumber = 1
            )
        }
        coEvery { repository.getTopRatedMovies(1) }.answers {
            PageResponse(
                page = 1,
                totalPages = 2,
                totalResults = 2,
                results = emptyList()
            )
        }

        // act
        testDispatcher.runBlockingTest {
            topRatedUseCaseTest.execute(1)
        }

        // assert
        coVerify{ mapper.map(any()) }
    }
}