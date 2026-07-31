package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.TvShowsRepository
import com.gabrielbmoro.moviedb.domain.model.TvShowDetail
import com.gabrielbmoro.moviedb.domain.model.TvShow
import com.gabrielbmoro.moviedb.domain.model.VideoStream

class FakeTvShowsRepository : TvShowsRepository {
    var tvShowDetail: TvShowDetail? = null
    var videoStreams: List<VideoStream> = emptyList()

    override suspend fun getTvShowsFromFilter(filter: String, page: Int): List<TvShow> {
        return emptyList()
    }

    override suspend fun getTvShowDetail(tvShowId: Long): TvShowDetail {
        return tvShowDetail ?: error("tvShowDetail not set")
    }

    override suspend fun getTvShowVideoStreams(tvShowId: Long): List<VideoStream> {
        return videoStreams
    }
}
