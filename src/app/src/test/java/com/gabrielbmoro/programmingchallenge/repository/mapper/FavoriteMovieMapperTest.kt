package com.gabrielbmoro.programmingchallenge.repository.mapper

import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.mappers.FavoriteMovieMapper
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
        val movie = Movie(
            id = 12L,
            isFavorite = true,
            overview = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various v",
            title = "Why do we use it?",
            releaseDate = "02-03-2020",
            posterImageUrl = "https://image/bucket/s3/ga2.png",
            backdropImageUrl = "https://image/bucket/s3/ga1.png",
            popularity = 10f,
            votesAverage = 5f,
            language = "en-US"
        )

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