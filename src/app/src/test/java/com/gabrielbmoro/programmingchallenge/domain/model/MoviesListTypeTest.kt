package com.gabrielbmoro.programmingchallenge.domain.model

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class MoviesListTypeTest {

    @Test
    fun `top rated movies must be a kind of movie`() {
        // given
        val topRatedMovieId = 1

        // when
        val type = topRatedMovieId.convertToMovieListType()

        // then
        assertThat(type).isEqualTo(MovieListType.TopRated)
    }

    @Test
    fun `favorite movies must be a kind of movie`() {
        // given
        val favoriteMovieId = 2

        // when
        val type = favoriteMovieId.convertToMovieListType()

        // then
        assertThat(type).isEqualTo(MovieListType.Favorite)
    }

    @Test
    fun `popular movies must be a kind of movie`() {
        // given
        val popularMovieId = 3

        // when
        val type = popularMovieId.convertToMovieListType()

        // then
        assertThat(type).isEqualTo(MovieListType.Popular)
    }

}