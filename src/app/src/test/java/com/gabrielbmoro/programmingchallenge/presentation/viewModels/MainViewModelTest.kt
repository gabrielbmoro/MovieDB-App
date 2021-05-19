package com.gabrielbmoro.programmingchallenge.presentation.viewModels

import com.gabrielbmoro.programmingchallenge.presentation.MainViewModel
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.junit.Before

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun init() {
        viewModel = MainViewModel()
    }

    @Test
    fun `select the popular movies tab`() {
        // given
        val tabIndex = MainViewModel.POPULAR_PAGE

        // when
        viewModel.setPage(tabIndex)

        //then
        assertThat(viewModel.getPage()).isEqualTo(MainViewModel.POPULAR_PAGE)
    }

    @Test
    fun `select the top rated movies tab`() {
        // given
        val tabIndex = MainViewModel.TOP_RATED_PAGE

        // when
        viewModel.setPage(tabIndex)

        // then
        assertThat(viewModel.getPage()).isEqualTo(MainViewModel.TOP_RATED_PAGE)
    }

    @Test
    fun `select the favorite movies tab`() {
        // given
        val tabIndex = MainViewModel.FAVORITE_PAGE

        // when
        viewModel.setPage(tabIndex)

        // then
        assertThat(viewModel.getPage()).isEqualTo(MainViewModel.FAVORITE_PAGE)
    }

}