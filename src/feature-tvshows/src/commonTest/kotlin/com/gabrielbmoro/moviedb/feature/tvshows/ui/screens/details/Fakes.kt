package com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.details

import com.gabrielbmoro.moviedb.domain.model.TvShowDetail
import com.gabrielbmoro.moviedb.domain.usecases.GetTvShowDetailsUseCase
import com.gabrielbmoro.moviedb.platform.logging.LoggerHelper
import kotlin.reflect.KClass

val fakeTvShowDetail = TvShowDetail(
    id = 123L,
    name = "Breaking Bad",
    votesAverage = 8.9f,
    posterImageUrl = "poster.jpg",
    backdropImageUrl = "backdrop.jpg",
    overview = "A chemistry teacher turned meth cook.",
    firstAirDate = "2008-01-20",
    lastAirDate = "2013-09-29",
    originalLanguage = "en",
    popularity = 100f,
    status = "Ended",
    tagline = "Remember my name",
    homepage = "https://breakingbad.com",
    genres = listOf("Drama", "Crime"),
    networks = listOf("AMC"),
    createdBy = listOf("Vince Gilligan"),
    productionCompanies = listOf("Sony Pictures"),
    numberOfSeasons = 5,
    numberOfEpisodes = 62,
    videoId = "trailer123",
)

val fakeTvShowDetailEmptyOptionals = TvShowDetail(
    id = 456L,
    name = "Minimal Show",
    votesAverage = 5f,
    posterImageUrl = null,
    backdropImageUrl = null,
    overview = "",
    firstAirDate = "",
    lastAirDate = "",
    originalLanguage = "",
    popularity = 0f,
    status = "",
    tagline = null,
    homepage = null,
    genres = emptyList(),
    networks = emptyList(),
    createdBy = emptyList(),
    productionCompanies = emptyList(),
    numberOfSeasons = 0,
    numberOfEpisodes = 0,
    videoId = null,
)

class FakeGetTvShowDetailsUseCase : GetTvShowDetailsUseCase {
    var result: Result<TvShowDetail> = Result.success(fakeTvShowDetail)

    override suspend fun execute(input: GetTvShowDetailsUseCase.Params): TvShowDetail {
        return result.getOrThrow()
    }
}

class FakeLoggerHelper : LoggerHelper {
    override fun plant(baseClass: KClass<*>) = Unit
    override fun logDebug(message: String) = Unit
    override fun logInfo(message: String) = Unit
    override fun logError(error: Throwable) = Unit
}
