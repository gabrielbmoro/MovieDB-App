package com.gabrielbmoro.programmingchallenge.domain.usecase

import com.gabrielbmoro.programmingchallenge.KoinUnitTest
import com.gabrielbmoro.programmingchallenge.repository.dataBase.DataBaseRepositoryImpl
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.koin.test.inject

class FavoriteMovieUseCaseTest : KoinUnitTest() {

    @Test
    fun `favoriteMovieUseCase using the correct repository`() {
        // given
        val favoriteUseCaseTest by inject<FavoriteMovieUseCase>()

        // when
        val repository = favoriteUseCaseTest.repository

        // then
        assertThat(repository).isInstanceOf(DataBaseRepositoryImpl::class.java)
    }
}