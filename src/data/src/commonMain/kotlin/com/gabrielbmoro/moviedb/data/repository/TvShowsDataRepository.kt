package com.gabrielbmoro.moviedb.data.repository

import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.ApiService
import com.gabrielbmoro.moviedb.data.repository.mappers.toTvShow
import com.gabrielbmoro.moviedb.domain.TvShowsRepository
import com.gabrielbmoro.moviedb.domain.model.TvShow

internal class TvShowsDataRepository(
    private val api: ApiService,
) : TvShowsRepository {

    override suspend fun getTvShowsFromFilter(filter: String, page: Int): List<TvShow> =
        api.getTvShows(filter, page).results?.map { it.toTvShow() }.orEmpty()
}
