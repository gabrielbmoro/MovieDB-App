package com.gabrielbmoro.programmingchallenge.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import android.app.Application
import com.gabrielbmoro.programmingchallenge.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.usecases.GetTopRatedMoviesUseCase
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val application: Application = mockk()
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase = mockk()
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase = mockk()
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk()

    private fun getViewModel(): MainViewModel {
        return MainViewModel(
            application,
            getFavoriteMoviesUseCase,
            getTopRatedMoviesUseCase,
            getPopularMoviesUseCase
        )
    }

    @Test
    fun `should be able to request all `() {
        val viewModelTest = getViewModel()


    }
}