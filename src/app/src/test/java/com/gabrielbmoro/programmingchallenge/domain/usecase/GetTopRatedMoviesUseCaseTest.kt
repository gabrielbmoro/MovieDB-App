package com.gabrielbmoro.programmingchallenge.domain.usecase

import com.gabrielbmoro.programmingchallenge.KoinUnitTest
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.api.ApiRepositoryImpl
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.koin.test.inject

class GetTopRatedMoviesUseCaseTest : KoinUnitTest() {

    @Test
    fun `topRatedUseCase using for the correct repository`() {
        // given
        val topRatedUseCaseTest by inject<GetTopRatedMoviesUseCase>()

        // when
        val given = topRatedUseCaseTest.repository

        // then
        Truth.assertThat(given).isInstanceOf(ApiRepositoryImpl::class.java)
    }

    @Test
    fun `topRatedUseCase calling for the correct method`() {
        // given
        val repository = mockk<MoviesRepository>()
        coEvery { repository.getTopRatedMovies(any()) }.returns(null)

        GlobalScope.launch {
            // when
            GetTopRatedMoviesUseCase(repository).execute(1)

            // then
            coVerify {
                repository.getTopRatedMovies(1)
            }
        }
    }
}