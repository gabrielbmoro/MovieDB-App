package com.gabrielbmoro.moviedb.data.repository.datasources.ktor

import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.MovieDetailResponse
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.PageResponse
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.responses.VideoStreamsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(
    private val baseUrl: String,
    private val httpClient: HttpClient,
) {

    suspend fun getMovies(category: String, pageNumber: Int): PageResponse =
        fetchMovie("$category?page=$pageNumber")

    suspend fun getVideoStreams(movieId: Long): VideoStreamsResponse = fetchMovie("$movieId/videos")

    suspend fun getMovieDetails(movieId: Long): MovieDetailResponse = fetchMovie("$movieId")

    suspend fun searchMovieBy(
        query: String,
        includeAdult: Boolean = false,
        language: String = "en-US",
    ): PageResponse =
        fetchData("search/movie?query=$query&include_adult=$includeAdult&language=$language")

    private suspend inline fun <reified T> fetchMovie(suffix: String): T =
        fetchData("movie/$suffix")

    private suspend inline fun <reified T> fetchData(endpoint: String): T =
        httpClient.get("$baseUrl/$endpoint").body()
}
