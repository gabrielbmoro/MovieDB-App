package com.gabrielbmoro.moviedb.domain

import com.gabrielbmoro.moviedb.data.repository.MoviesDataRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import com.gabrielbmoro.moviedb.domain.mappers.toFavoriteMovieDTO
import com.gabrielbmoro.moviedb.domain.mappers.toMovie
import com.gabrielbmoro.moviedb.domain.mappers.toMovieDetail
import com.gabrielbmoro.moviedb.domain.mappers.toVideoStreams
import org.koin.core.annotation.Factory

interface MoviesRepository {
    suspend fun getMoviesFromFilter(filter: String, page: Int): Result<List<Movie>>

    suspend fun getFavoriteMovies(): Result<List<Movie>>

    suspend fun favorite(movie: Movie): Result<Unit>

    suspend fun unFavorite(movieTitle: String): Result<Unit>

    suspend fun checkIsAFavoriteMovie(movieTitle: String): Result<Boolean>

    suspend fun getVideoStreams(movieId: Long): Result<List<VideoStream>>

    suspend fun getMovieDetail(movieId: Long): Result<MovieDetail>

    suspend fun searchMovieBy(query: String): Result<List<Movie>>
}

@Factory(binds = [MoviesRepository::class])
internal class MoviesRepositoryImpl(
    private val dataRepository: MoviesDataRepository,
) : MoviesRepository {
    override suspend fun getMoviesFromFilter(
        filter: String,
        page: Int,
    ): Result<List<Movie>> {
        return runCatching {
            dataRepository.getMoviesFromFilter(
                filter = filter,
                page = page,
            )?.map { it.toMovie() }.orEmpty()
        }
    }

    override suspend fun getFavoriteMovies(): Result<List<Movie>> {
        return runCatching {
            dataRepository.getFavoriteMovies().map { it.toMovie() }
        }
    }

    override suspend fun favorite(movie: Movie): Result<Unit> {
        return runCatching {
            dataRepository.favorite(movie.toFavoriteMovieDTO())
        }
    }

    override suspend fun unFavorite(movieTitle: String): Result<Unit> {
        return runCatching {
            dataRepository.unFavorite(movieTitle)
        }
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Result<Boolean> {
        return runCatching {
            dataRepository.checkIsAFavoriteMovie(movieTitle)
        }
    }

    override suspend fun getVideoStreams(movieId: Long): Result<List<VideoStream>> {
        return runCatching {
            dataRepository.getVideoStreams(movieId).toVideoStreams()
        }
    }

    override suspend fun getMovieDetail(movieId: Long): Result<MovieDetail> {
        return runCatching {
            dataRepository.getMovieDetail(movieId).toMovieDetail()
        }
    }

    override suspend fun searchMovieBy(query: String): Result<List<Movie>> {
        return runCatching {
            dataRepository.searchMovieBy(query)?.map { it.toMovie() }.orEmpty()
        }
    }
}
