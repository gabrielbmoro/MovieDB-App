package com.gabrielbmoro.moviedb.search.di

import com.gabrielbmoro.moviedb.repository.MoviesRepository
import com.gabrielbmoro.moviedb.search.domain.SearchMovieUseCase
import com.gabrielbmoro.moviedb.search.domain.SearchMovieUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SearchMovieModule {

    @Provides
    fun searchMovieUseCase(repository: MoviesRepository): SearchMovieUseCase {
        return SearchMovieUseCaseImpl(repository)
    }
}