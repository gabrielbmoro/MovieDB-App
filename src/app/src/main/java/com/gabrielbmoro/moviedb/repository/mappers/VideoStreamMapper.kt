package com.gabrielbmoro.moviedb.repository.mappers

import com.gabrielbmoro.moviedb.domain.model.VideoStream
import com.gabrielbmoro.moviedb.repository.retrofit.responses.VideoStreamsResponse

class VideoStreamMapper {

    fun map(videoStreamResponse: VideoStreamsResponse) = videoStreamResponse.results.map {
        VideoStream(
            key = it.key,
            site = it.site,
        )
    }
}