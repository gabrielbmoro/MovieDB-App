package com.gabrielbmoro.moviedb.data.di

import com.gabrielbmoro.moviedb.data.providers.databaseInstance
import com.gabrielbmoro.moviedb.data.providers.httpClientEngine
import com.gabrielbmoro.moviedb.data.repository.MoviesRepositoryImpl
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.FavoriteMoviesDAO
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.ApiService
import com.gabrielbmoro.moviedb.domain.MoviesRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule =
    module {
        single {
            HttpClient(
                engine = httpClientEngine(),
            ) {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.HEADERS
                }

                install(ContentNegotiation) {
                    json(
                        json =
                        Json {
                            ignoreUnknownKeys = true
                        },
                    )
                }

                install(Auth) {
                    bearer {
                        loadTokens {
                            BearerTokens(
                                get(named("api_token")),
                                "",
                            )
                        }
                    }
                }
            }
        }

        single {
            ApiService(
                baseUrl = "https://api.themoviedb.org/3",
                httpClient = get(),
            )
        }

        single<FavoriteMoviesDAO> {
            databaseInstance().favoriteMoviesDAO()
        }

        single<MoviesRepository> {
            MoviesRepositoryImpl(
                api = get(),
                favoriteMoviesDAO = get(),
            )
        }
    }
