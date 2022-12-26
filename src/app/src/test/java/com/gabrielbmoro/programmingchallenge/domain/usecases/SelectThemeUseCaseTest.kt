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
class SelectThemeUseCaseTest {

    private lateinit var repository: MoviesRepository
    private lateinit var useCase: SelectThemeUseCase

    @Before
    fun before() {
        repository = mockk()
        useCase = SelectThemeUseCase(repository)
    }

    @Test
    fun `should be able to select a night theme`() {
        // arrange
        val theme = ThemeType.NIGHT

        coEvery { repository.selectTheme(theme) }.returns(
            Unit
        )

        runTest {
            // act
            useCase(themeType = theme)

            // assert
            coVerify(exactly = 1) { repository.selectTheme(theme) }
        }
    }
}