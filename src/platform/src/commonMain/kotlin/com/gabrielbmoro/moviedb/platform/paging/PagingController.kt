package com.gabrielbmoro.moviedb.platform.paging

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class PagingController<E>(
    val coroutineScope: CoroutineScope,
    val ioCoroutineDispatcher: CoroutineDispatcher,
    val requestMore: suspend (Int) -> List<E>
) {
    private var currentPage = 0

    suspend fun onRequestMore(): List<E> {
        currentPage++
        return coroutineScope.async(ioCoroutineDispatcher) {
            requestMore(currentPage)
        }.await()
    }

    fun reset() {
        currentPage = 0
    }
}
