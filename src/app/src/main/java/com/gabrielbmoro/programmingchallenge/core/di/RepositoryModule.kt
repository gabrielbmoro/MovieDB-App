package com.gabrielbmoro.programmingchallenge.core.di

import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepositoryImpl
import com.gabrielbmoro.programmingchallenge.repository.mappers.FavoriteMovieMapper
import com.gabrielbmoro.programmingchallenge.repository.mappers.MovieMapper
import com.gabrielbmoro.programmingchallenge.repository.mappers.PageMapper
import com.gabrielbmoro.programmingchallenge.repository.retrofit.ApiRepository
import com.gabrielbmoro.programmingchallenge.repository.room.FavoriteMoviesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepositoryInstance(
        apiRepository: ApiRepository,
        favoriteMoviesDAO: FavoriteMoviesDAO,
        resourcesProvider: ResourcesProvider,
        favoriteMoviesMapper: FavoriteMovieMapper,
        pageMapper: PageMapper,
        movieMapper: MovieMapper
    ): MoviesRepository {
        return MoviesRepositoryImpl(
            api = apiRepository,
            apiToken = resourcesProvider.getString(R.string.api_token),
            favoriteMoviesDAO = favoriteMoviesDAO,
            favoriteMoviesMapper = favoriteMoviesMapper,
            pageMapper = pageMapper,
            movieMapper = movieMapper,
        )
    }
}