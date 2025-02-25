package com.gabrielbmoro.moviedb.movies.domain.repository

interface MoviesPageRepository {
    fun setCurrentPage(page: Int)
    fun getCurrentPage(): Int?
}
