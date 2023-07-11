package com.gabrielbmoro.moviedb.repository.mapper

import com.gabrielbmoro.moviedb.domain.model.VideoStream
import com.gabrielbmoro.moviedb.repository.mappers.VideoStreamMapper
import com.gabrielbmoro.moviedb.repository.retrofit.responses.VideoStreamsResponse
import com.gabrielbmoro.moviedb.repository.retrofit.responses.VideoStreamsResponseItem
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class VideoStreamMapperTest {

    @Test
    fun `should be able to convert a video stream response to a video stream objects`() {
        // arrange
        val expectedSite = "YouTube"
        val expectedKey = "w2g13hg"

        val mapper = VideoStreamMapper()

        // act
        val result = mapper.map(
            VideoStreamsResponse(
                results = listOf(
                    VideoStreamsResponseItem(
                        id = "",
                        site = expectedSite,
                        key = expectedKey,
                        name = "Test",
                        size = 1200,
                        type = "test",
                        official = true
                    )
                )
            )
        ).first()

        // assert
        Truth.assertThat(result.site).isEqualTo(expectedSite)
        Truth.assertThat(result.key).isEqualTo(expectedKey)
    }
}