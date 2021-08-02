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
class GetPopularMoviesUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val mapper: PageMapper = mockk()
    private val testDispatcher = TestCoroutineDispatcher()

    private fun getPopularMovieUseCaseTest() = GetPopularMoviesUseCase(repository, mapper)

    @Test
    fun `Get popular movies with more than one page`() {
        // arrange
        val popularUseCaseTest = getPopularMovieUseCaseTest()
        coEvery {
            mapper.map(any())
        }.answers {
            Page(
                movies = listOf(),
                totalPages = 2,
                pageNumber = 1
            )
        }
        coEvery { repository.getPopularMovies(1) }.answers {
            PageResponse(
                page = 1,
                totalPages = 2,
                totalResults = 2,
                results = emptyList()
            )
        }

        // act
        testDispatcher.runBlockingTest {
            popularUseCaseTest.execute(1)
        }

        // assert
        coVerify{ mapper.map(any()) }
    }
}