package com.gabrielbmoro.programmingchallenge.repository.mapper

import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.MovieResponse
import com.gabrielbmoro.programmingchallenge.repository.room.dto.FavoriteMovieDTO
import com.gabrielbmoro.programmingchallenge.repository.mappers.MovieMapper
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
        val favoriteMovieDTO = FavoriteMovieDTO(
            overview = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various v",
            title = "Why do we use it?",
            releaseDate = "02-03-2020",
            backdropImageUrl = "https://image/bucket/s3/ga.png",
            posterImageUrl = "https://image/bucket/s3/ga.png",
            popularity = 10f,
            votesAverage = 5f,
            language = "en-US"
        )

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
        val movieResponse = MovieResponse(
            id = 12L,
            overview = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various v",
            title = "Why do we use it?",
            originalTitle = "Why do we use it?",
            releaseDate = "02-03-2020",
            backdropPath = "https://image/bucket/s3/ga.png",
            popularity = 10f,
            votesAverage = 5f,
            originalLanguage = "en-US",
            isAdult = false,
            votes = 2,
            isVideo = false,
            posterPath = "https://image/bucket/s3/ga.png"
        )

        // act
        val result = MovieMapper().mapResponse(movieResponse)

        // assert
        Truth.assertThat(result.backdropImageUrl).isEqualTo(movieResponse.backdropPath)
        Truth.assertThat(result.posterImageUrl).isEqualTo(movieResponse.posterPath)
        Truth.assertThat(result.language).isEqualTo(movieResponse.originalLanguage)
        Truth.assertThat(result.title).isEqualTo(movieResponse.title)
        Truth.assertThat(result.overview).isEqualTo(movieResponse.overview)
        Truth.assertThat(result.releaseDate).isEqualTo(movieResponse.releaseDate)
        Truth.assertThat(result.popularity).isEqualTo(movieResponse.popularity)
        Truth.assertThat(result.votesAverage).isEqualTo(movieResponse.votesAverage)
    }
}