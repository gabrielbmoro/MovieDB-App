package com.gabrielbmoro.moviedb.repository.mappers

import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.VideoStreamsResponse

class VideoStreamMapper {

    fun map(videoStreamResponse: VideoStreamsResponse) = videoStreamResponse.results.map {
        VideoStream(
            key = it.key,
            site = it.site,
            size = it.size,
            type = it.type,
            official = it.official,
            name = it.name,
            id = it.id
        )
    }
}
