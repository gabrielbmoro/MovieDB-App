package com.gabrielbmoro.moviedb.movies.di

import com.gabrielbmoro.moviedb.movies.domain.usecases.GetNowPlayingMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetPopularMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetTopRatedMoviesUseCase
import com.gabrielbmoro.moviedb.movies.domain.usecases.GetUpcomingMoviesUseCase
import com.gabrielbmoro.moviedb.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Provides
    fun getNowPlayingMoviesUseCase(repository: MoviesRepository): GetNowPlayingMoviesUseCase {
        return GetNowPlayingMoviesUseCase(repository)
    }

    @Provides
    fun getPopularMoviesUseCase(repository: MoviesRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(repository)
    }

    @Provides
    fun getTopRatedMoviesUseCase(repository: MoviesRepository): GetTopRatedMoviesUseCase {
        return GetTopRatedMoviesUseCase(repository)
    }

    @Provides
    fun getUpcomingMoviesUseCase(repository: MoviesRepository): GetUpcomingMoviesUseCase {
        return GetUpcomingMoviesUseCase(repository)
    }
}
