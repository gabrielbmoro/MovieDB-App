package com.gabrielbmoro.moviedb.details.ui.screens.details

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.domain.model.MovieDetail
import com.gabrielbmoro.moviedb.domain.model.VideoStream
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

class FakeRepository : MoviesRepository {
    var isFavorite: Boolean = false

    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int,
    ): List<Movie> {
        error("Not yet implemented")
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        error("Not yet implemented")
    }

    override suspend fun favorite(movie: Movie) = Unit

    override suspend fun unFavorite(movieTitle: String) = Unit

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
        return isFavorite
    }

    override suspend fun getVideoStreams(movieId: Long): List<VideoStream> {
        error("Not yet implemented")
    }

    override suspend fun getMovieDetail(movieId: Long): MovieDetail {
        error("Not yet implemented")
    }

    override suspend fun searchMovieBy(query: String): List<Movie> {
        error("Not yet implemented")
    }
}

class FakeGetMovieDetailsUseCase : GetMovieDetailsUseCase {
    lateinit var result: MovieDetail

    override suspend fun execute(input: GetMovieDetailsUseCase.Params): MovieDetail {
        return result
    }
}
