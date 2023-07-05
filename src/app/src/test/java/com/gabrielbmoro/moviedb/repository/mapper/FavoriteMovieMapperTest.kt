package com.gabrielbmoro.moviedb.repository.mapper

import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.repository.mappers.FavoriteMovieMapper
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FavoriteMovieMapperTest {

    @Test
    fun `should be able to convert a movie to a dto`() {
        // arrange
        val movie = Movie.mockWhyDoWeUseItFavorite()

        // act
        val result = FavoriteMovieMapper().map(id = 2, movie = movie)

        // assert
        Truth.assertThat(result.posterImageUrl).isEqualTo(movie.posterImageUrl)
        Truth.assertThat(result.backdropImageUrl).isEqualTo(movie.backdropImageUrl)
        Truth.assertThat(result.language).isEqualTo(movie.language)
        Truth.assertThat(result.title).isEqualTo(movie.title)
        Truth.assertThat(result.overview).isEqualTo(movie.overview)
        Truth.assertThat(result.releaseDate).isEqualTo(movie.releaseDate)
        Truth.assertThat(result.popularity).isEqualTo(movie.popularity)
        Truth.assertThat(result.votesAverage).isEqualTo(movie.votesAverage)
        Truth.assertThat(result.id).isEqualTo(2)
    }
}