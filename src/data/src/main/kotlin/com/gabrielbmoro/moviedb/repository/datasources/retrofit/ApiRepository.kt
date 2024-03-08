package com.gabrielbmoro.moviedb.repository.datasources.retrofit

import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.MovieDetailResponse
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.PageResponse
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.VideoStreamsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The inferface that defines the requests to API.
 * @author Gabriel Moro
 * @since 2018-08-30
 */
interface ApiRepository {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") pageNumber: Int
    ): PageResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") pageNumber: Int
    ): PageResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") pageNumber: Int
    ): PageResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") pageNumber: Int
    ): PageResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getVideoStreams(
        @Path("movie_id") movieId: Long
    ): VideoStreamsResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long
    ): MovieDetailResponse

    @GET("search/movie")
    suspend fun searchMovieBy(
        @Query("query")
        query: String,
        @Query("include_adult")
        includeAdult: Boolean = false,
        @Query("language")
        language: String = "en-US"
    ): PageResponse
}
