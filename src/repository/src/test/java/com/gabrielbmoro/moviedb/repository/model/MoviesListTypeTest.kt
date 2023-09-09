package com.gabrielbmoro.moviedb.repository.model

import com.google.common.truth.Truth
import org.junit.Test

class MoviesListTypeTest {

    @Test
    fun `should be able to identify a top rated movie`() {
        // arrange
        val topRatedMovieId = 1

        // act
        val type = topRatedMovieId.convertToMovieListType()

        // assert
        Truth.assertThat(type).isEqualTo(MovieListType.TOP_RATED)
    }

    @Test
    fun `should be able to identify a favorite movie`() {
        // arrange
        val favoriteMovieId = 2

        // act
        val type = favoriteMovieId.convertToMovieListType()

        // assert
        Truth.assertThat(type).isEqualTo(MovieListType.FAVORITE)
    }

    @Test
    fun `should be able to identify a upcoming movie`() {
        // arrange
        val favoriteMovieId = 4

        // act
        val type = favoriteMovieId.convertToMovieListType()

        // assert
        Truth.assertThat(type).isEqualTo(MovieListType.UPCOMING)
    }

    @Test
    fun `should be able to identify a popular movie`() {
        // arrange
        val popularMovieId = 3

        // act
        val type = popularMovieId.convertToMovieListType()

        // assert
        Truth.assertThat(type).isEqualTo(MovieListType.POPULAR)
    }

    @Test
    fun `should not be able to identify a unknown movie`() {
        // arrange
        val popularMovieId = -1

        // act
        val type = popularMovieId.convertToMovieListType()

        // assert
        Truth.assertThat(type).isNull()
    }

}