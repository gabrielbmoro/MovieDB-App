package com.gabrielbmoro.moviedb.movies.data

import com.gabrielbmoro.moviedb.movies.domain.repository.MoviesPageRepository

class MoviesPageRepositoryImpl(
    private val dataSource: MoviesPageDataSource,
): MoviesPageRepository {

    override fun setCurrentPage(page: Int) {
        dataSource.page = page
    }

    override fun getCurrentPage(): Int? = dataSource.page
}
