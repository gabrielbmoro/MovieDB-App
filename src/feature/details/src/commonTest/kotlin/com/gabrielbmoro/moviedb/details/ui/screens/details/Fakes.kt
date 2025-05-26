package com.gabrielbmoro.moviedb.details.ui.screens.details

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase

val fakeMovieDetail = MovieDetail(
    adult = false,
    budget = 1200,
    genres = listOf("genre1"),
    homepage = "homePage",
    imdbId = "imdbId",
    status = "status",
    tagline = "tagline",
    productionCompanies = listOf("productionCompany1"),
    overview = "overview",
    title = "title",
    votesAverage = 12f,
    releaseDate = "10/02/1990",
    backdropImageUrl = "backgropImageUrl",
    language = "language",
    popularity = 2f,
    posterImageUrl = "posterImageUrl",
)

class FakeFavoriteMovieUseCase : FavoriteMovieUseCase {
    override suspend fun execute(input: FavoriteMovieUseCase.Params) = Unit
}

class FakeRepository: MoviesRepository{
    var isFavorite: Boolean = false

    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int,
    ): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun favorite(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun unFavorite(movieTitle: String) {
        TODO("Not yet implemented")
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
        return isFavorite
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

class FakeGetMovieDetailsUseCase : GetMovieDetailsUseCase {
    lateinit var result: MovieDetail

    override suspend fun execute(input: GetMovieDetailsUseCase.Params): MovieDetail {
        return result
    }
}
