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

class GetPopularMoviesUseCaseTest : KoinUnitTest() {

    @Test
    fun `popularMoviesUseCase using the correct repository`() {
        // given
        val popularUseCaseTest by inject<GetPopularMoviesUseCase>()

        // when
        val repository = popularUseCaseTest.repository

        // then
        Truth.assertThat(repository).isInstanceOf(ApiRepositoryImpl::class.java)
    }

    @Test
    fun `popularMoviesUseCase calling for the correct method`() {
        // given
        val repository = mockk<MoviesRepository>()
        coEvery { repository.getPopularMovies(any()) }.returns(null)

        GlobalScope.launch {
            // when
            GetPopularMoviesUseCase(repository).execute(1)

            // then
            coVerify {
                repository.getPopularMovies(1)
            }
        }

    }

}