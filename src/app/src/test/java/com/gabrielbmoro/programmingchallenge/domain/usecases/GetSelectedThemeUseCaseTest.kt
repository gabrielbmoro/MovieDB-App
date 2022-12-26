package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.model.ThemeType
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GetSelectedThemeUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: GetSelectedThemeUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = GetSelectedThemeUseCase(repository)
    }

    @Test
    fun `should be able to select a night theme`() {
        // arrange
        coEvery { repository.getCurrentTheme() }.returns(ThemeType.NIGHT)

        runTest {
            // act
            useCase()

            // assert
            coVerify(exactly = 1) { repository.getCurrentTheme() }
        }
    }
}