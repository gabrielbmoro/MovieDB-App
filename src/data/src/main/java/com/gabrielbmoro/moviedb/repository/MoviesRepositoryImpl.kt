package com.gabrielbmoro.moviedb.repository

import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.ApiRepository
import com.gabrielbmoro.moviedb.repository.datasources.room.FavoriteMoviesDAO
import com.gabrielbmoro.moviedb.repository.mappers.FavoriteMovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.MovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.VideoDetailsMapper
import com.gabrielbmoro.moviedb.repository.mappers.VideoStreamMapper

class MoviesRepositoryImpl(
    private val api: ApiRepository,
    private val favoriteMoviesDAO: FavoriteMoviesDAO,
    private val favoriteMoviesMapper: FavoriteMovieMapper,
    private val movieMapper: MovieMapper,
    private val videoStreamMapper: VideoStreamMapper,
    private val videoDetailsMapper: VideoDetailsMapper
) : MoviesRepository {

    override suspend fun getFavoriteMovies(): List<Movie> {
        return favoriteMoviesDAO.allFavoriteMovies().map { data ->
            movieMapper.mapFavorite(data)
        }
    }

    override suspend fun getPopularMovies(page: Int): List<Movie> {
        val movies = api.getPopularMovies(pageNumber = page)
        return movies.results?.map { movieResponse ->
            movieMapper.mapResponse(movieResponse)
        } ?: emptyList()
    }

    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        val movies = api.getTopRatedMovies(pageNumber = page)
        return movies.results?.map { movieResponse ->
            movieMapper.mapResponse(movieResponse)
        } ?: emptyList()
    }

    override suspend fun getUpcomingMovies(page: Int): List<Movie> {
        val movies = api.getUpcomingMovies(pageNumber = page)
        return movies.results?.map { movieResponse ->
            movieMapper.mapResponse(movieResponse)
        } ?: emptyList()
    }

    override suspend fun getNowPlayingMovies(page: Int): List<Movie> {
        val movies = api.getNowPlayingMovies(pageNumber = page)
        return movies.results?.map { movieResponse ->
            movieMapper.mapResponse(movieResponse)
        } ?: emptyList()
    }

    override suspend fun favorite(movie: Movie) {
        val movieDTO = favoriteMoviesMapper.map(
            movie = movie
        )
        favoriteMoviesDAO.saveFavorite(movieDTO)
    }

    override suspend fun unFavorite(movieTitle: String) {
        favoriteMoviesDAO.removeFavorite(movieTitle)
    }

    override suspend fun checkIsAFavoriteMovie(movieTitle: String): Boolean {
        return favoriteMoviesDAO.isThereAMovie(
            title = movieTitle
        ).any()
    }

    override suspend fun getVideoStreams(movieId: Long): List<VideoStream> {
        val result = api.getVideoStreams(
            movieId = movieId
        )
        return videoStreamMapper.map(result)
    }

    override suspend fun getMovieDetail(movieId: Long): MovieDetail {
        val result = api.getMovieDetails(
            movieId = movieId
        )
        return videoDetailsMapper.map(result)
    }

    override suspend fun searchMovieBy(query: String): List<Movie> {
        val result = api.searchMovieBy(query = query)
        val movies = result.results?.map { movieMapper.mapResponse(it) } ?: emptyList()
        return movies
    }
}
