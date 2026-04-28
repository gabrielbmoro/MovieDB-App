package com.gabrielbmoro.moviedb.data.providers

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
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

internal fun buildHttpClient(
    apiToken: String,
) : HttpClient {
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

expect fun httpClientEngine(): HttpClientEngine
