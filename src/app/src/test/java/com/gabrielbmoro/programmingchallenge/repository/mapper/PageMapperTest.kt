package com.gabrielbmoro.programmingchallenge.repository.mapper

import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import com.gabrielbmoro.programmingchallenge.repository.mappers.MovieMapper
import com.gabrielbmoro.programmingchallenge.repository.mappers.PageMapper
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PageMapperTest {

    lateinit var movieMapper: MovieMapper

    @Before
    fun before() {
        movieMapper = MovieMapper()
    }

    @Test
    fun `should be able to convert page response to page object`() {
        // arrange
        val pageResponse = PageResponse.mockPageWithWhyDoWeUseItMovieOnly()

        // act
        val result = PageMapper(movieMapper).map(pageResponse)

        // assert
        Truth.assertThat(result.totalPages).isEqualTo(pageResponse.totalPages)
        Truth.assertThat(result.pageNumber).isEqualTo(pageResponse.page)
    }
}