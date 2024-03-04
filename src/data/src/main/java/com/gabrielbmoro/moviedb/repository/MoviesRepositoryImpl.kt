package com.gabrielbmoro.moviedb.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.entities.VideoStream
import com.gabrielbmoro.moviedb.repository.datasources.paging.MoviesDataSource
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.ApiRepository
import com.gabrielbmoro.moviedb.repository.datasources.room.FavoriteMoviesDAO
import com.gabrielbmoro.moviedb.repository.mappers.FavoriteMovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.MovieMapper
import com.gabrielbmoro.moviedb.repository.mappers.PageMapper
import com.gabrielbmoro.moviedb.repository.mappers.VideoDetailsMapper
import com.gabrielbmoro.moviedb.repository.mappers.VideoStreamMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(
    private val api: ApiRepository,
    private val favoriteMoviesDAO: FavoriteMoviesDAO,
    private val favoriteMoviesMapper: FavoriteMovieMapper,
    private val pageMapper: PageMapper,
    private val movieMapper: MovieMapper,
    private val videoStreamMapper: VideoStreamMapper,
    private val videoDetailsMapper: VideoDetailsMapper
) : MoviesRepository {

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMoviesDAO.allFavoriteMovies().map { data ->
            data.map {
                movieMapper.mapFavorite(it)
            }
        }
    }

    override fun getPopularMovies(): Flow<PagingData<Movie>> = getPagingData(
        MoviesDataSource.PagingType.POPULAR_MOVIES
    )

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> =
        getPagingData(
            MoviesDataSource.PagingType.TOP_RATED_MOVIES
        )

    override fun getUpcomingMovies(): Flow<PagingData<Movie>> =
        getPagingData(
            MoviesDataSource.PagingType.COMING_SOON
        )

    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> =
        getPagingData(
            MoviesDataSource.PagingType.NOW_PLAYING
        )

    private fun getPagingData(pagingType: MoviesDataSource.PagingType): Flow<PagingData<Movie>> {
        val pagingDataFlow = Pager(
            pagingSourceFactory = {
                MoviesDataSource(
                    api = api,
                    pagingType = pagingType
                )
            },
            initialKey = 1,
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE
            )
        ).flow
        return pageMapper.map(pagingDataFlow)
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

    override fun getVideoStreams(movieId: Long): Flow<List<VideoStream>> {
        return flow {
            val result = api.getVideoStreams(
                movieId = movieId
            )
            val data = videoStreamMapper.map(result)
            emit(data)
        }
    }

    override fun getMovieDetail(movieId: Long): Flow<MovieDetail> {
        return flow {
            val result = api.getMovieDetails(
                movieId = movieId
            )
            val data = videoDetailsMapper.map(result)
            emit(data)
        }
    }

    override fun searchMovieBy(query: String): Flow<List<Movie>> = flow {
        val result = api.searchMovieBy(query = query)
        val movies = result.results?.map { movieMapper.mapResponse(it) } ?: emptyList()
        emit(movies)
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_SIZE = 500
    }
}
