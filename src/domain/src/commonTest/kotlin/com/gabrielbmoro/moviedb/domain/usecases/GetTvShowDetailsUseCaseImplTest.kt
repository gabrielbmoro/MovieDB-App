package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.model.TvShowDetail
import com.gabrielbmoro.moviedb.domain.model.VideoStream
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetTvShowDetailsUseCaseImplTest {
    private lateinit var repository: FakeTvShowsRepository
    private lateinit var useCase: GetTvShowDetailsUseCase

    private val validVideoStream =
        VideoStream(
            id = "0L",
            type = "Trailer",
            official = true,
            size = 12,
            site = "YouTube",
            name = "name",
            key = "a0s9d",
        )
    private val nonOfficialStream =
        VideoStream(
            id = "1L",
            type = "Trailer",
            official = true,
            size = 12,
            site = "Twitter",
            name = "other",
            key = "b1t2e",
        )

    @BeforeTest
    fun before() {
        repository = FakeTvShowsRepository()
        useCase = GetTvShowDetailsUseCaseImpl(repository)
    }

    @Test
    fun `should enrich detail with videoId from official YouTube trailer`() =
        runTest {
            val detail = TvShowDetail(
                id = 123L,
                name = "Breaking Bad",
                votesAverage = 8.9f,
                posterImageUrl = "poster.jpg",
                backdropImageUrl = "backdrop.jpg",
                overview = "A chemistry teacher...",
                firstAirDate = "2008-01-20",
                lastAirDate = "2013-09-29",
                originalLanguage = "en",
                popularity = 100f,
                status = "Ended",
                tagline = "Remember my name",
                homepage = "https://breakingbad.com",
                genres = listOf("Drama"),
                networks = listOf("AMC"),
                createdBy = listOf("Vince Gilligan"),
                productionCompanies = listOf("Sony"),
                numberOfSeasons = 5,
                numberOfEpisodes = 62,
                videoId = null,
            )
            repository.tvShowDetail = detail
            repository.videoStreams = listOf(validVideoStream, nonOfficialStream)

            val result = useCase.execute(GetTvShowDetailsUseCase.Params(123L))

            val expected = detail.copy(videoId = validVideoStream.key)
            assertEquals(expected, result)
        }

    @Test
    fun `should return null videoId when no matching trailer exists`() =
        runTest {
            val detail = TvShowDetail(
                id = 456L,
                name = "Show",
                votesAverage = 7f,
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
            repository.tvShowDetail = detail
            repository.videoStreams = listOf(nonOfficialStream)

            val result = useCase.execute(GetTvShowDetailsUseCase.Params(456L))

            assertEquals(detail.copy(videoId = null), result)
        }

    @Test
    fun `should return null videoId when videos list is empty`() =
        runTest {
            val detail = TvShowDetail(
                id = 789L,
                name = "Empty Show",
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
            repository.tvShowDetail = detail
            repository.videoStreams = emptyList()

            val result = useCase.execute(GetTvShowDetailsUseCase.Params(789L))

            assertEquals(detail.copy(videoId = null), result)
        }
}
