package com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.details

import com.gabrielbmoro.moviedb.domain.usecases.GetTvShowDetailsUseCase
import com.gabrielbmoro.moviedb.platform.logging.LoggerHelper
import com.gabrielbmoro.moviedb.platform.viewmodel.BaseViewModel
import com.gabrielbmoro.moviedb.platform.viewmodel.UiEvent
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher

class TvShowDetailsViewModel(
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val loggerHelper: LoggerHelper,
) : BaseViewModel<TvShowDetailsUIState, TvShowDetailsUserIntent, UiEvent>(ioDispatcher) {

    init {
        loggerHelper.plant(this::class)
    }

    override fun defaultEmptyState() = TvShowDetailsUIState.empty()

    override fun onFailure(throwable: Throwable) {
        loggerHelper.logError(throwable)
    }

    override fun executeIntent(intent: TvShowDetailsUserIntent) {
        when (intent) {
            is TvShowDetailsUserIntent.LoadTvShowDetails -> launchIo { loadTvShowDetails(intent) }
            is TvShowDetailsUserIntent.HideVideo -> launchIo { hideVideo() }
        }
    }

    private suspend fun loadTvShowDetails(intent: TvShowDetailsUserIntent.LoadTvShowDetails) {
        updateState { it.copy(isLoading = true) }

        val result = runCatching {
            getTvShowDetailsUseCase.execute(GetTvShowDetailsUseCase.Params(tvShowId = intent.tvShowId))
        }

        result.fold(
            onSuccess = { detail ->
                updateState {
                    it.copy(
                        isLoading = false,
                        tvShowName = detail.name,
                        votesAverage = detail.votesAverage,
                        language = detail.originalLanguage,
                        popularity = detail.popularity,
                        overview = detail.overview,
                        imageUrl = detail.backdropImageUrl,
                        tagline = detail.tagline,
                        genres = detail.genres.toImmutableList(),
                        status = detail.status,
                        firstAirDate = detail.firstAirDate,
                        lastAirDate = detail.lastAirDate,
                        networks = detail.networks.toImmutableList(),
                        createdBy = detail.createdBy.toImmutableList(),
                        productionCompanies = detail.productionCompanies.toImmutableList(),
                        homepage = detail.homepage,
                        numberOfSeasons = detail.numberOfSeasons,
                        numberOfEpisodes = detail.numberOfEpisodes,
                        videoId = detail.videoId,
                        showVideo = detail.videoId != null,
                        errorMessage = null,
                    )
                }
            },
            onFailure = { error ->
                loggerHelper.logError(error)
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Something went wrong",
                    )
                }
            },
        )
    }

    private suspend fun hideVideo() {
        updateState { it.copy(showVideo = false) }
    }
}
