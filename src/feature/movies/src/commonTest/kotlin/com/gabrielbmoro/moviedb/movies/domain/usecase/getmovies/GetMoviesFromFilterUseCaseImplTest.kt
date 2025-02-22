package com.gabrielbmoro.moviedb.movies.domain.usecase.getmovies

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMoviesFromFilterUseCaseImplTest {

    private val movie1 = Movie.mockWhiteDragonNotFavorite()
    private val movie2 = Movie.mockWhyDoWeUseItFavorite()

    private val mockedMovies: List<Movie> = listOf(movie1, movie2)

    private val useCase = GetMoviesFromFilterUseCaseImpl(
        object : MoviesRepository {
            override suspend fun getMoviesFromFilter(filter: String, page: Int): List<Movie> =
                mockedMovies

            override suspend fun getFavoriteMovies(): List<Movie> = emptyList()

            override suspend fun favorite(movie: Movie) {
                TODO("Not yet implemented")
            }

            override suspend fun unFavorite(movieTitle: String) {
                TODO("Not yet implemented")
            }

            override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
                TODO("Not yet implemented")
            }

            override suspend fun getVideoStreams(movieId: Long): List<VideoStream> {
                TODO("Not yet implemented")
            }

            override suspend fun getMovieDetail(movieId: Long): MovieDetail {
                TODO("Not yet implemented")
            }

            override suspend fun searchMovieBy(query: String): List<Movie> {
                TODO("Not yet implemented")
            }

        }
    )

    @Test
    fun `should return the movies from filter`() = runTest {
        val result = useCase.execute(
            GetMoviesFromFilterUseCase.Params(
                filter = FilterType.NowPlaying,
                page = 1,
            ),
        )

        assertEquals(mockedMovies, result)
    }
}
