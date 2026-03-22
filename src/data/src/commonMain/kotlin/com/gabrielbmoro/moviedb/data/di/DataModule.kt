package com.gabrielbmoro.moviedb.data.di

import com.gabrielbmoro.moviedb.data.BuildKonfig
import com.gabrielbmoro.moviedb.data.providers.AppDatabase
import com.gabrielbmoro.moviedb.data.providers.databaseInstance
import com.gabrielbmoro.moviedb.data.providers.httpClientEngine
import com.gabrielbmoro.moviedb.data.repository.MoviesDataRepository
import com.gabrielbmoro.moviedb.data.repository.datasources.database.room.FavoriteMoviesDAO
import com.gabrielbmoro.moviedb.data.repository.datasources.ktor.ApiService
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
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

private const val BASE_URL = "https://api.themoviedb.org/3"

@Module
class DataModule {

    @Factory
    fun moviesDataRepository(
        favoriteMoviesDAO: FavoriteMoviesDAO,
        apiService: ApiService,
    ) = MoviesDataRepository(
        favoriteMoviesDAO = favoriteMoviesDAO,
        api = apiService,
    )

    @Single
    fun providesDatabase() = databaseInstance()

    @Single
    fun providesFavoriteDAO(appDatabase: AppDatabase): FavoriteMoviesDAO {
        return appDatabase.favoriteMoviesDAO()
    }

    @Single
    fun providesHttpClient(): HttpClient {
        val apiToken = BuildKonfig.API_TOKEN
        return HttpClient(
            engine = httpClientEngine(),
        ) {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.HEADERS
            }

            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    },
                )
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            apiToken,
                            "",
                        )
                    }
                }
            }
        }
    }

    @Single
    fun providesApiService(
        httpClient: HttpClient,
    ) = ApiService(
        baseUrl = BASE_URL,
        httpClient = httpClient,
    )
}
