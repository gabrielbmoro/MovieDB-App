package com.gabrielbmoro.moviedb.repository.mappers

import androidx.paging.PagingData
import androidx.paging.map
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PageMapper(
    private val movieMapper: MovieMapper
) {
    fun map(pagingData: Flow<PagingData<MovieResponse>>) =
        pagingData.map { pagingDataMoviesResponse ->
            pagingDataMoviesResponse.map { movieResponse ->
                movieMapper.mapResponse(movieResponse)
            }
        }
}
