package com.gabrielbmoro.programmingchallenge.entities

import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.repository.entities.convertToMovieListType
import com.google.common.truth.Truth
import org.junit.Test

class MoviesListTypeTest {

    @Test
    fun `top rated movies must be a kind of movie`() {
        // arrange
        val topRatedMovieId = 1

        // act
        val type = topRatedMovieId.convertToMovieListType()

        // assert
        Truth.assertThat(type).isEqualTo(MovieListType.TopRated)
    }

    @Test
    fun `favorite movies must be a kind of movie`() {
        // arrange
        val favoriteMovieId = 2

        // act
        val type = favoriteMovieId.convertToMovieListType()

        // assert
        Truth.assertThat(type).isEqualTo(MovieListType.Favorite)
    }

    @Test
    fun `popular movies must be a kind of movie`() {
        // arrange
        val popularMovieId = 3

        // act
        val type = popularMovieId.convertToMovieListType()

        // assert
        Truth.assertThat(type).isEqualTo(MovieListType.Popular)
    }

    @Test
    fun `unknown movie should not be recognized`() {
        // arrange
        val popularMovieId = 4

        // act
        val type = popularMovieId.convertToMovieListType()

        // assert
        Truth.assertThat(type).isNull()
    }

}