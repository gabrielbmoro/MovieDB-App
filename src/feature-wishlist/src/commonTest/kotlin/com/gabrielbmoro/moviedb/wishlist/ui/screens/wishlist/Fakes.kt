package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.model.Movie
import com.gabrielbmoro.moviedb.domain.model.MovieDetail
import com.gabrielbmoro.moviedb.domain.model.VideoStream
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

    override suspend fun favorite(movie: Movie) = Unit

    override suspend fun unFavorite(movieTitle: String) = Unit

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
        return isFavorite
    }

    override suspend fun getVideoStreams(movieId: Long): List<VideoStream> {
        return emptyList()
    }

    override suspend fun getMovieDetail(movieId: Long): MovieDetail {
        error("Something went wrong")
    }

    override suspend fun searchMovieBy(query: String): List<Movie> {
        error("Something went wrong")
    }
}

class FakeFavoriteMovieUseCase : FavoriteMovieUseCase {
    var timesCalled: Int = 0
        private set

    override suspend fun execute(input: FavoriteMovieUseCase.Params) {
        timesCalled++
    }
}
