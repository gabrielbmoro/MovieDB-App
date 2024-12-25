package com.gabrielbmoro.moviedb.platform.paging

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PagingController {
    private var _currentPageFlow = MutableStateFlow(0)
    val currentPage: StateFlow<Int> get() = _currentPageFlow

    fun onRequestMore() {
        _currentPageFlow.update {
            it + 1
        }
    }

    fun reset() {
        _currentPageFlow.update { 0 }
    }
}
