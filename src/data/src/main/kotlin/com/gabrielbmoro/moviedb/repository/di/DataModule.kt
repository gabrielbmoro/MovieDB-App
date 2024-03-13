package com.gabrielbmoro.moviedb.repository.di

import androidx.room.Room
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.repository.MoviesRepositoryImpl
import com.gabrielbmoro.moviedb.repository.datasources.ktor.ApiService
import com.gabrielbmoro.moviedb.repository.datasources.ktor.interceptors.AuthenticationInterceptor
import com.gabrielbmoro.moviedb.repository.datasources.ktor.interceptors.LoggedInterceptor
import com.gabrielbmoro.moviedb.repository.datasources.room.DataBaseFactory
import com.gabrielbmoro.moviedb.repository.datasources.room.MIGRATION_1_2
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            DataBaseFactory::class.java,
            "MovieDBAppDataBase"
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
            .favoriteMoviesDAO()
    }

    single {
        HttpClient(
            engine = OkHttpEngine(
                config = OkHttpConfig().apply {
                    addInterceptor(AuthenticationInterceptor(get(named("api_token"))))
                    addInterceptor(LoggedInterceptor())
                }
            )
        ) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    single {
        ApiService(
            httpClient = get(),
            baseUrl = "https://api.themoviedb.org/3"
        )
    }

    single {
        MoviesRepositoryImpl(
            api = get(),
            favoriteMoviesDAO = get()
        )
    }.bind(MoviesRepository::class)
}
