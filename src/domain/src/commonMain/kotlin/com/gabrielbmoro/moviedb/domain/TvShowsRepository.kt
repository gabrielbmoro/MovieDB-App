package com.gabrielbmoro.moviedb.domain

import com.gabrielbmoro.moviedb.domain.model.TvShow
import com.gabrielbmoro.moviedb.domain.model.TvShowDetail
import com.gabrielbmoro.moviedb.domain.model.VideoStream

interface TvShowsRepository {
    suspend fun getTvShowsFromFilter(filter: String, page: Int): List<TvShow>
    suspend fun getTvShowDetail(tvShowId: Long): TvShowDetail
    suspend fun getTvShowVideoStreams(tvShowId: Long): List<VideoStream>
}
