package com.gabrielbmoro.programmingchallenge.repository.retrofit

import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The inferface that defines the requests to API.
 * @author Gabriel Moro
 * @since 2018-08-30
 */
interface ApiRepository {

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ) : PageResponse

    @GET("discover/movie?sort_by=vote_average.desc")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ) : PageResponse
}