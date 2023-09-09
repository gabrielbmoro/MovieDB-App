package com.gabrielbmoro.moviedb.repository.datasources.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.ApiRepository
import com.gabrielbmoro.moviedb.repository.datasources.retrofit.responses.MovieResponse

class MoviesDataSource(
    private val api: ApiRepository,
    private val pagingType: PagingType
) : PagingSource<Int, MovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val nextPageNumber = params.key ?: START_PAGE_INDEX

        return try {
            val result = when (pagingType) {
                PagingType.POPULAR_MOVIES -> {
                    api.getPopularMovies(
                        pageNumber = nextPageNumber
                    )
                }

                PagingType.TOP_RATED_MOVIES -> {
                    api.getTopRatedMovies(
                        pageNumber = nextPageNumber
                    )
                }

                PagingType.COMING_SOON -> {
                    api.getUpcomingMovies(
                        pageNumber = nextPageNumber
                    )
                }

                PagingType.NOW_PLAYING -> {
                    api.getNowPlayingMovies(
                        pageNumber = nextPageNumber
                    )
                }
            }
            LoadResult.Page(
                data = result.results ?: emptyList(),
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = nextPageNumber.plus(1)
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    enum class PagingType {
        POPULAR_MOVIES, TOP_RATED_MOVIES, COMING_SOON, NOW_PLAYING
    }

    companion object {
        private const val START_PAGE_INDEX = 1
    }
}