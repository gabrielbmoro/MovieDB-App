package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.domain.entities.Movie
import timber.log.Timber

class PagingController(
    val tag: String,
    val requestMore: suspend (Int) -> List<Movie>,
) {
    private var currentPage = 0

    suspend fun onRequestMore(): List<Movie> {
        currentPage++
        val movies = requestMore(currentPage)
        Timber.tag(tag).d("Requesting more, page $currentPage -> Movies size: ${movies.size}")
        return movies
    }
}