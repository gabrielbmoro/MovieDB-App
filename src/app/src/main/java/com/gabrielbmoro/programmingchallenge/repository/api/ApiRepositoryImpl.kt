package com.gabrielbmoro.programmingchallenge.repository.api

import androidx.paging.DataSource
import com.gabrielbmoro.programmingchallenge.core.ConfigVariables
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.api.response.PageResponse

class ApiRepositoryImpl(private val apiRepository: ApiRepository) : MoviesRepository {

    override fun getFavoriteMovies(): DataSource.Factory<Int, Movie>? = null

    override suspend fun getPopularMovies(pageNumber: Int): PageResponse {
        return apiRepository.getMovies(
                pageNumber = pageNumber,
                apiKey = ConfigVariables.TOKEN,
                sortBy = ConfigVariables.PARAMETER_POPULAR_MOVIES
        )
    }

    override suspend fun getTopRatedMovies(pageNumber: Int): PageResponse {
        return apiRepository.getMovies(
                pageNumber = pageNumber,
                apiKey = ConfigVariables.TOKEN,
                sortBy = ConfigVariables.PARAMETER_TOP_RATED_MOVIES
        )
    }

    override suspend fun doAsFavorite(movie: Movie) = false

    override suspend fun unFavorite(movie: Movie) = false

    override suspend fun checkIsAFavoriteMovie(movie: Movie) = false

}