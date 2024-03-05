package com.gabrielbmoro.moviedb.movies.ui.screens.movies

import com.gabrielbmoro.moviedb.domain.entities.Movie
import java.util.concurrent.atomic.AtomicBoolean

class PagingController(
    val requestMore: suspend (Int) -> List<Movie>,
) {
    private var currentPage = 0
    private val lock = AtomicBoolean(false)

    suspend fun onRequestMore(): List<Movie> {
        val movies: List<Movie>
        if (!lock.get()) {
            lock.set(true)
            currentPage++
            movies = requestMore(currentPage)
            lock.set(false)
        } else {
            movies = emptyList()
        }
        return movies
    }
}