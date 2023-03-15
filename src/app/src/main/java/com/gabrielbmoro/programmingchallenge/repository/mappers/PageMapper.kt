package com.gabrielbmoro.programmingchallenge.repository.mappers

import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageMapper @Inject constructor(
    private val movieMapper: MovieMapper
) {
    fun map(pageResponse: PageResponse): Page {
        return Page(
            totalPages = pageResponse.totalPages,
            pageNumber = pageResponse.page,
            movies = pageResponse.results?.mapNotNull {
                val targetMovie = movieMapper.mapResponse(it)
                if (targetMovie.isValid) targetMovie else null
            } ?: emptyList()
        )
    }
}