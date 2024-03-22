package com.gabrielbmoro.moviedb.data.repository.datasources.ktor

import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.MovieDetailResponse
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.PageResponse
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.VideoStreamsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(
    private val baseUrl: String,
    private val httpClient: HttpClient
) {
    suspend fun getPopularMovies(pageNumber: Int): PageResponse {
        return httpClient.get("$baseUrl/movie/popular?page=$pageNumber").body()
    }

    suspend fun getTopRatedMovies(pageNumber: Int): PageResponse {
        return httpClient.get("$baseUrl/movie/top_rated?page=$pageNumber").body()
    }

    suspend fun getUpcomingMovies(pageNumber: Int): PageResponse {
        return httpClient.get("$baseUrl/movie/upcoming?page=$pageNumber").body()
    }

    suspend fun getNowPlayingMovies(pageNumber: Int): PageResponse {
        return httpClient.get("$baseUrl/movie/now_playing?page=$pageNumber").body()
    }

    suspend fun getVideoStreams(movieId: Long): VideoStreamsResponse {
        return httpClient.get("$baseUrl/movie/$movieId/videos").body()
    }

    suspend fun getMovieDetails(movieId: Long): MovieDetailResponse {
        return httpClient.get("$baseUrl/movie/$movieId").body()
    }

    suspend fun searchMovieBy(query: String, includeAdult: Boolean = false, language: String = "en-US"): PageResponse {
        return httpClient.get("$baseUrl/search/movie?query=$query&include_adult=$includeAdult&language=$language").body()
    }
}