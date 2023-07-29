package com.gabrielbmoro.moviedb.repository.retrofit

import com.gabrielbmoro.moviedb.repository.retrofit.responses.PageResponse
import com.gabrielbmoro.moviedb.repository.retrofit.responses.VideoStreamsResponse
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
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): PageResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): PageResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): PageResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getVideoStreams(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String,
    ): VideoStreamsResponse
}