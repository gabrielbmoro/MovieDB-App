package com.gabrielbmoro.moviedb.feature.tvshows.fakes

import com.gabrielbmoro.moviedb.domain.TvShowsRepository
import com.gabrielbmoro.moviedb.domain.model.TvShow

class FakeTvShowsRepository(
    private val getTvShowsFromFilterError: Throwable? = null,
) : TvShowsRepository {

    lateinit var filteredTvShows: List<TvShow>

    override suspend fun getTvShowsFromFilter(
        filter: String,
        page: Int,
    ): List<TvShow> {
        getTvShowsFromFilterError?.let {
            throw it
        }
        return filteredTvShows
    }
}
