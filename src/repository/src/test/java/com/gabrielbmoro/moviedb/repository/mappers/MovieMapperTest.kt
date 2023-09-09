package com.gabrielbmoro.moviedb.repository.mappers

import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.MovieResponse
import com.gabrielbmoro.moviedb.repository.datasources.room.dto.FavoriteMovieDTO
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieMapperTest {

    @Test
    fun `should be able to convert dto to movie object`() {
        // arrange
        val favoriteMovieDTO = FavoriteMovieDTO.mockWhyDoWeUseMovie()

        // act
        val result = MovieMapper().mapFavorite(favoriteMovieDTO)

        // assert
        Truth.assertThat(result.backdropImageUrl).isEqualTo(favoriteMovieDTO.backdropImageUrl)
        Truth.assertThat(result.posterImageUrl).isEqualTo(favoriteMovieDTO.posterImageUrl)
        Truth.assertThat(result.language).isEqualTo(favoriteMovieDTO.language)
        Truth.assertThat(result.title).isEqualTo(favoriteMovieDTO.title)
        Truth.assertThat(result.overview).isEqualTo(favoriteMovieDTO.overview)
        Truth.assertThat(result.releaseDate).isEqualTo(favoriteMovieDTO.releaseDate)
        Truth.assertThat(result.popularity).isEqualTo(favoriteMovieDTO.popularity)
        Truth.assertThat(result.votesAverage).isEqualTo(favoriteMovieDTO.votesAverage)
    }

    @Test
    fun `should be able to convert a response to movie object`() {
        // arrange
        val movieResponse = MovieResponse.mockWhyDoWeUseMovieResponse()

        // act
        val result = MovieMapper().mapResponse(movieResponse)

        // assert
        Truth.assertThat(result.backdropImageUrl).isEqualTo(MovieMapper.BIG_SIZE_IMAGE_ADDRESS.plus(movieResponse.backdropPath))
        Truth.assertThat(result.posterImageUrl).isEqualTo(MovieMapper.SMALL_SIZE_IMAGE_ADDRESS.plus(movieResponse.posterPath))
        Truth.assertThat(result.language).isEqualTo(movieResponse.originalLanguage)
        Truth.assertThat(result.title).isEqualTo(movieResponse.title)
        Truth.assertThat(result.overview).isEqualTo(movieResponse.overview)
        Truth.assertThat(result.releaseDate).isEqualTo(movieResponse.releaseDate)
        Truth.assertThat(result.popularity).isEqualTo(movieResponse.popularity)
        Truth.assertThat(result.votesAverage).isEqualTo(movieResponse.votesAverage)
    }
}