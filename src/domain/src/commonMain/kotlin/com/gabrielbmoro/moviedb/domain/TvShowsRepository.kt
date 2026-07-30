package com.gabrielbmoro.moviedb.domain

import com.gabrielbmoro.moviedb.domain.model.TvShow

interface TvShowsRepository {
    suspend fun getTvShowsFromFilter(filter: String, page: Int): List<TvShow>
}
