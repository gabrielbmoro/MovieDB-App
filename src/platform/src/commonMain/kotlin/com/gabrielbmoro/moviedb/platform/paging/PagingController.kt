package com.gabrielbmoro.moviedb.platform.paging

import kotlinx.coroutines.flow.StateFlow

interface PagingController {
    val currentPage: StateFlow<Int>

    fun requestNextPage()

    fun resetPaging()
}
