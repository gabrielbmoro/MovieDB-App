package com.gabrielbmoro.programmingchallenge.domain.usecase.mapper

import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.repository.api.response.MovieResponse
import com.gabrielbmoro.programmingchallenge.repository.api.response.PageResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object MoviesMapper {

    private val dateParser = SimpleDateFormat("yyyy-mm-dd", Locale.UK)
    private val dateFormatter = SimpleDateFormat("dd/mm/yyyy", Locale.UK)

    fun mapToPage(response: PageResponse?): Page? {
        return response?.let { pageResponse ->
            Page(
                    movies = pageResponse.results?.map {
                        mapToMovie(it)
                    } ?: emptyList(),
                    hasMorePages = pageResponse.page < pageResponse.totalPages
            )
        }
    }

    fun mapToMovie(movieResponse: MovieResponse): Movie {
        return Movie(
                votes = movieResponse.votes ?: 0,
                votesAverage = movieResponse.votesAverage ?: 0f,
                isVideo = movieResponse.isVideo ?: false,
                releaseDate = formatReleaseDate(movieResponse.releaseDate),
                overview = movieResponse.overview ?: "",
                backdropPath = movieResponse.backdropPath ?: "",
                isAdult = movieResponse.isAdult ?: false,
                originalTitle = movieResponse.originalTitle ?: "",
                originalLanguage = movieResponse.originalLanguage ?: "",
                posterPath = movieResponse.posterPath ?: "",
                popularity = movieResponse.popularity ?: 0f,
                title = movieResponse.title ?: ""
        )
    }

    fun formatReleaseDate(dateFromResponse: String?): String {
        return dateFromResponse?.let { response ->
            try {
                dateParser.parse(response)?.let { dt ->
                    dateFormatter.format(dt)
                }
            } catch (parseException: ParseException) {
                ""
            }
        } ?: ""
    }
}