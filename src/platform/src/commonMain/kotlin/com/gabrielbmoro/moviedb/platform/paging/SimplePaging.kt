package com.gabrielbmoro.moviedb.platform.paging

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

open class SimplePaging(private val firstPage: Int) : PagingController {

    private val _currentPageFlow = MutableStateFlow(firstPage)
    override val currentPage: StateFlow<Int> get() = _currentPageFlow

    override fun requestNextPage() {
        _currentPageFlow.update { it.inc() }
    }

    override fun resetPaging() {
        _currentPageFlow.update { firstPage }
    }
}
