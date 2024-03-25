package com.gabrielbmoro.moviedb.platform.paging

class PagingController<E>(
    val requestMore: suspend (Int) -> List<E>,
) {
    private var currentPage = 0

    suspend fun onRequestMore(): List<E> {
        currentPage++
        return requestMore(currentPage)
    }
}