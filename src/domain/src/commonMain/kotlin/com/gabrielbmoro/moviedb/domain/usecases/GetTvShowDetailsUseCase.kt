package com.gabrielbmoro.moviedb.domain.usecases

import com.gabrielbmoro.moviedb.domain.TvShowsRepository
import com.gabrielbmoro.moviedb.domain.model.TvShowDetail
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

interface GetTvShowDetailsUseCase : UseCase<GetTvShowDetailsUseCase.Params, TvShowDetail> {
    data class Params(
        val tvShowId: Long,
    )
}

@Factory(binds = [GetTvShowDetailsUseCase::class])
internal class GetTvShowDetailsUseCaseImpl(
    @Provided private val repository: TvShowsRepository,
) : GetTvShowDetailsUseCase {
    override suspend fun execute(input: GetTvShowDetailsUseCase.Params): TvShowDetail {
        val tvShowDetail = repository.getTvShowDetail(input.tvShowId)
        val videoStream = repository.getTvShowVideoStreams(input.tvShowId)
            .firstOrNull { videoStream ->
                videoStream.site == SITE_KEY && videoStream.official && videoStream.type == TYPE_KEY
            }

        return tvShowDetail.copy(
            videoId = videoStream?.key,
        )
    }

    companion object {
        private const val SITE_KEY = "YouTube"
        private const val TYPE_KEY = "Trailer"
    }
}
