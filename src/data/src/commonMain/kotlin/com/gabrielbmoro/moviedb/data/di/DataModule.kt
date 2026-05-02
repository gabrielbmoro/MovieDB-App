package com.gabrielbmoro.moviedb.data.di

import com.gabrielbmoro.moviedb.data.BuildKonfig
import com.gabrielbmoro.moviedb.data.providers.buildHttpClient
import com.gabrielbmoro.moviedb.data.providers.databaseInstance
import com.gabrielbmoro.moviedb.data.repository.MoviesDataRepository
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.ApiService
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import org.koin.dsl.module

private const val BASE_URL = "https://api.themoviedb.org/3"

val dataModule = module {
    factory<MoviesRepository> {
        MoviesDataRepository(
            favoriteMoviesDAO = get(),
            api = get(),
        )
    }

    single { databaseInstance().favoriteMoviesDAO() }

    single {
        buildHttpClient(
            apiToken = BuildKonfig.API_TOKEN,
        )
    }

    single {
        ApiService(
            baseUrl = BASE_URL,
            httpClient = get(),
        )
    }
}
