package com.gabrielbmoro.moviedb.domain.entities

import kotlin.test.Test
import kotlin.test.assertEquals

class MoviesListTypeTest {
    @Test
    fun `should be able to identify a top rated movie`() {
        // arrange
        val topRatedMovieId = 1

        // act
        val result = topRatedMovieId.convertToMovieListType()

        // assert
        assertEquals(MovieListType.TOP_RATED, result)
    }

    @Test
    fun `should be able to identify a favorite movie`() {
        // arrange
        val favoriteMovieId = 2

        // act
        val result = favoriteMovieId.convertToMovieListType()

        // assert
        assertEquals(MovieListType.FAVORITE, result)
    }

    @Test
    fun `should be able to identify a upcoming movie`() {
        // arrange
        val favoriteMovieId = 4

        // act
        val result = favoriteMovieId.convertToMovieListType()

        // assert
        assertEquals(MovieListType.UPCOMING, result)
    }

    @Test
    fun `should be able to identify a popular movie`() {
        // arrange
        val popularMovieId = 3

        // act
        val result = popularMovieId.convertToMovieListType()

        // assert
        assertEquals(MovieListType.POPULAR, result)
    }

    @Test
    fun `should not be able to identify a unknown movie`() {
        // arrange
        val popularMovieId = -1

        // act
        val result = popularMovieId.convertToMovieListType()

        // assert
        assertEquals(null, result)
    }
}
