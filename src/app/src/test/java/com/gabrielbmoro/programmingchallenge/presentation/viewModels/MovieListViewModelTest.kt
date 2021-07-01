package com.gabrielbmoro.programmingchallenge.presentation.viewModels

import com.gabrielbmoro.programmingchallenge.KoinUnitTest
import com.gabrielbmoro.programmingchallenge.domain.usecase.GetFavoriteMoviesUseCase
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.usecase.GetPopularMoviesUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecase.GetTopRatedMoviesUseCase
import com.gabrielbmoro.programmingchallenge.presentation.movieList.MovieListViewModel
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.koin.test.inject

class MovieListViewModelTest : KoinUnitTest() {

    private val topRatedUseCase by inject<GetTopRatedMoviesUseCase>()
    private val favoriteUseCase by inject<GetFavoriteMoviesUseCase>()
    private val popularMoviesUseCase by inject<GetPopularMoviesUseCase>()

    @Test
    fun `the list of movies can be reloaded`() {
        val viewModel = MovieListViewModel(
            MovieListType.TopRated,
            getTopRatedMoviesUseCase = topRatedUseCase,
            getPopularMoviesUseCase = popularMoviesUseCase,
            getFavoriteMoviesUseCase = favoriteUseCase
        )

        viewModel.reload()

        assertThat(viewModel.currentPage).isEqualTo(MovieListViewModel.FIRST_PAGE)
        assertThat(viewModel.previousSize)
        assertThat(viewModel.type).isEqualTo(MovieListType.TopRated)
    }

}