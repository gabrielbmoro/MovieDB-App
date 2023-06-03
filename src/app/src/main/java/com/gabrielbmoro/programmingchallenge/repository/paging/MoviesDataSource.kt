package com.gabrielbmoro.programmingchallenge.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gabrielbmoro.programmingchallenge.repository.retrofit.ApiRepository
import com.gabrielbmoro.programmingchallenge.repository.retrofit.responses.MovieResponse

class MoviesDataSource(
    private val api: ApiRepository,
    private val apiKey: String,
    private val pagingType: PagingType
) : PagingSource<Int, MovieResponse>() {

    override val keyReuseSupported: Boolean
        get() = true

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val pageNumber = params.key ?: START_PAGE_INDEX

        return try {
            val result = when (pagingType) {
                PagingType.POPULAR_MOVIES -> {
                    api.getPopularMovies(
                        apiKey = apiKey,
                        pageNumber = pageNumber
                    )
                }

                PagingType.TOP_RATED_MOVIES -> {
                    api.getTopRatedMovies(
                        apiKey = apiKey,
                        pageNumber = pageNumber
                    )
                }
            }
            LoadResult.Page(
                data = result.results ?: emptyList(),
                prevKey = pageNumber,
                nextKey = pageNumber.plus(1)
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    enum class PagingType {
        POPULAR_MOVIES, TOP_RATED_MOVIES
    }

    companion object {
        private const val START_PAGE_INDEX = 1
    }
}