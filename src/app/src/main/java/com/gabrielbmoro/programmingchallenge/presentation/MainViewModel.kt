package com.gabrielbmoro.programmingchallenge.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private var selectedPage = TOP_RATED_PAGE

    fun setPage(pageIndex: Int) {
        selectedPage = pageIndex
    }

    fun getPage() = selectedPage

    companion object {
        const val TOP_RATED_PAGE = 0
        const val POPULAR_PAGE = 1
        const val FAVORITE_PAGE = 2
    }
}