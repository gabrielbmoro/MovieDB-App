package com.gabrielbmoro.programmingchallenge.domain.usecase.mapper

import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.repository.api.response.MovieResponse
import com.gabrielbmoro.programmingchallenge.repository.api.response.PageResponse
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MoviesMapperTest {

    @Test
    fun `mapper cannot convert from null values`() {
        // given
        val nullValue = null

        // when
        val result = MoviesMapper.mapToPage(nullValue)

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `mapper can convert from an empty page response`() {
        // given
        val page = PageResponse(
                totalResults = 0,
                totalPages = 0,
                page = 0,
                results = emptyList()
        )

        // when
        val result = MoviesMapper.mapToPage(page)

        // then
        assertThat(result).isEqualTo(
                Page(
                        movies = emptyList(),
                        hasMorePages = false
                )
        )
    }

    @Test
    fun `mapper can convert from an empty movie response`() {
        // given
        val movie = MovieResponse(
                isVideo = null,
                releaseDate = null,
                overview = null,
                isAdult = null,
                backdropPath = null,
                originalLanguage = null,
                originalTitle = null,
                posterPath = null,
                popularity = null,
                votesAverage = null,
                title = null,
                votes = null
        )

        // when
        val result = MoviesMapper.mapToMovie(movie)

        // then
        assertThat(result.isVideo).isFalse()
        assertThat(result.releaseDate).isEmpty()
        assertThat(result.overview).isEmpty()
        assertThat(result.isAdult).isFalse()
        assertThat(result.backdropPath).isEmpty()
        assertThat(result.originalLanguage).isEmpty()
        assertThat(result.originalTitle).isEmpty()
        assertThat(result.posterPath).isEmpty()
        assertThat(result.popularity).isZero()
        assertThat(result.votesAverage).isZero()
        assertThat(result.title).isEmpty()
        assertThat(result.votes).isEqualTo(0)
    }

    @Test
    fun `mapper can convert from a movie response`() {
        // given
        val movieResponse = MovieResponse(
                isVideo = false,
                releaseDate = "2019-20-02",
                overview = "A pair of troubled school boys navigate the chaos of love and friendship in the early 80's.",
                isAdult = false,
                backdropPath = null,
                originalLanguage = "en",
                originalTitle = "Choir Boyz",
                posterPath = "/gUbBFW1BlXSEmjEMJjMzU9XVKdM.jpg",
                popularity = 0f,
                votesAverage = 0f,
                title = "Choir Boyz",
                votes = 0
        )

        // when
        val result = MoviesMapper.mapToMovie(movieResponse)

        // then
        assertThat(result.isVideo).isFalse()
        assertThat(result.releaseDate).isEqualTo("02/20/2019")
        assertThat(result.overview).isEqualTo("A pair of troubled school boys navigate the chaos of love and friendship in the early 80's.")
        assertThat(result.isAdult).isFalse()
        assertThat(result.backdropPath).isEmpty()
        assertThat(result.originalLanguage).isEqualTo("en")
        assertThat(result.originalTitle).isEqualTo("Choir Boyz")
        assertThat(result.posterPath).isEqualTo("/gUbBFW1BlXSEmjEMJjMzU9XVKdM.jpg")
        assertThat(result.popularity).isZero()
        assertThat(result.votesAverage).isZero()
        assertThat(result.title).isEqualTo("Choir Boyz")
        assertThat(result.votes).isEqualTo(0)
    }

    @Test
    fun `data cannot be converted from an invalid format`() {
        // given
        val given = "asodk-02-2012"

        // when
        val result = MoviesMapper.formatReleaseDate(given)

        // then
        assertThat("").isEqualTo(result)
    }


    @Test
    fun `data cannot be converted from null format`() {
        // given
        val given = null

        // when
        val result = MoviesMapper.formatReleaseDate(given)

        // then
        assertThat("").isEqualTo(result)
    }

    @Test
    fun `data can be converted to dd-mm-yyyy`() {
        // given
        val given = "2017-02-12"

        // when
        val result = MoviesMapper.formatReleaseDate(given)

        // then
        assertThat("12/02/2017").isEqualTo(result)
    }
}