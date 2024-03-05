package com.gabrielbmoro.moviedb.repository

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.MovieResponse
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.VideoStreamsResponse
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.VideoStreamsResponseItem
import com.gabrielbmoro.moviedb.repository.datasources.room.dto.FavoriteMovieDTO
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MappersExtTest {

    @Test
    fun `should be able to convert a movie to a dto`() {
        // arrange
        val movie = Movie.mockWhyDoWeUseItFavorite()

        // act
        val result = movie.toFavoriteMovieDTO(customId = 2)

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

    @Test
    fun `should be able to convert dto to movie object`() {
        // arrange
        val favoriteMovieDTO = FavoriteMovieDTO.mockWhyDoWeUseMovie()

        // act
        val result = favoriteMovieDTO.toMovie()

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
        val result = movieResponse.toMovie()

        // assert
        Truth.assertThat(result.backdropImageUrl)
            .isEqualTo(BIG_SIZE_IMAGE_ADDRESS.plus(movieResponse.backdropPath))
        Truth.assertThat(result.posterImageUrl)
            .isEqualTo(SMALL_SIZE_IMAGE_ADDRESS.plus(movieResponse.posterPath))
        Truth.assertThat(result.language).isEqualTo(movieResponse.originalLanguage)
        Truth.assertThat(result.title).isEqualTo(movieResponse.title)
        Truth.assertThat(result.overview).isEqualTo(movieResponse.overview)
        Truth.assertThat(result.releaseDate).isEqualTo(movieResponse.releaseDate)
        Truth.assertThat(result.popularity).isEqualTo(movieResponse.popularity)
        Truth.assertThat(result.votesAverage).isEqualTo(movieResponse.votesAverage)
    }

    @Test
    fun `should be able to convert a video stream response to a video stream objects`() {
        // arrange
        val expectedSite = "YouTube"
        val expectedKey = "w2g13hg"
        val response = VideoStreamsResponse(
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

        // act
        val result = response.toVideoStreams().first()

        // assert
        Truth.assertThat(result.site).isEqualTo(expectedSite)
        Truth.assertThat(result.key).isEqualTo(expectedKey)
    }
}