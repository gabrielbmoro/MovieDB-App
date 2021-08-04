package com.gabrielbmoro.programmingchallenge.usecases.mappers

import com.gabrielbmoro.programmingchallenge.repository.entities.Page
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
            movies = pageResponse.results?.map {
                movieMapper.mapResponse(it)
            } ?: emptyList()
        )
    }
}