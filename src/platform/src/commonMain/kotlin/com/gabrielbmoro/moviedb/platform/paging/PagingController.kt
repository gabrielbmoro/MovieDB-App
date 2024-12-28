package com.gabrielbmoro.moviedb.platform.paging

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface PagingController {
    val currentPage: StateFlow<Int>

    fun requestNextPage()

    fun resetPaging()
}

class SimplePaging : PagingController {
    private var _currentPageFlow = MutableStateFlow(0)
    override val currentPage: StateFlow<Int> get() = _currentPageFlow

    override fun requestNextPage() {
        _currentPageFlow.update {
            it + 1
        }
    }

    override fun resetPaging() {
        _currentPageFlow.update { 0 }
    }
}
