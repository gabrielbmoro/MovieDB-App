package com.gabrielbmoro.programmingchallenge.repository.mappers

import com.gabrielbmoro.programmingchallenge.domain.model.Page
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.PageResponse

class PageMapper constructor(
    private val movieMapper: MovieMapper
) {
    fun map(pageResponse: PageResponse): Page {
        return Page(
            totalPages = pageResponse.totalPages,
            pageNumber = pageResponse.page,
            movies = pageResponse.results?.map {
                movieMapper.mapResponse(it)
            } ?: emptyList()
        )
    }
}