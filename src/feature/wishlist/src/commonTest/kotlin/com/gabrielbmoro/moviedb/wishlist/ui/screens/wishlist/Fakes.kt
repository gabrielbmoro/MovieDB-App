package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase

class FakeRepository : MoviesRepository {
    lateinit var favoriteMovies: List<Movie>
    var isFavorite: Boolean = false

    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int,
    ): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        return favoriteMovies
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

class FakeFavoriteMovieUseCase : FavoriteMovieUseCase {
    var timesCalled: Int = 0
        private set

    override suspend fun execute(input: FavoriteMovieUseCase.Params) {
        timesCalled++
    }
}
