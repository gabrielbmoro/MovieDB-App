package com.gabrielbmoro.programmingchallenge.repository.mappers

import androidx.paging.PagingData
import androidx.paging.map
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PageMapper constructor(
    private val movieMapper: MovieMapper
) {
    fun map(pagingData: Flow<PagingData<MovieResponse>>) =
        pagingData.map { pagingDataMoviesResponse ->
            pagingDataMoviesResponse.map { movieResponse ->
                movieMapper.mapResponse(movieResponse)
            }
        }
}