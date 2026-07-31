package com.gabrielbmoro.moviedb.data.repository

import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.ApiService
import com.gabrielbmoro.moviedb.data.repository.mappers.toTvShow
import com.gabrielbmoro.moviedb.data.repository.mappers.toTvShowDetail
import com.gabrielbmoro.moviedb.data.repository.mappers.toVideoStreams
import com.gabrielbmoro.moviedb.domain.TvShowsRepository
import com.gabrielbmoro.moviedb.domain.model.TvShow
import com.gabrielbmoro.moviedb.domain.model.TvShowDetail
import com.gabrielbmoro.moviedb.domain.model.VideoStream

internal class TvShowsDataRepository(
    private val api: ApiService,
) : TvShowsRepository {

    override suspend fun getTvShowsFromFilter(filter: String, page: Int): List<TvShow> =
        api.getTvShows(filter, page).results?.map { it.toTvShow() }.orEmpty()

    override suspend fun getTvShowDetail(tvShowId: Long): TvShowDetail =
        api.getTvShowDetails(tvShowId).toTvShowDetail()

    override suspend fun getTvShowVideoStreams(tvShowId: Long): List<VideoStream> =
        api.getTvShowVideoStreams(tvShowId).toVideoStreams()
}
