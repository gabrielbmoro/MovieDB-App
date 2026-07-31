@file:Suppress("LongMethod")

package com.gabrielbmoro.moviedb.feature.tvshowdetails.ui.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.desingsystem.buttons.TextUrl
import com.gabrielbmoro.moviedb.desingsystem.cards.GenresCard
import com.gabrielbmoro.moviedb.desingsystem.error.ErrorMessage
import com.gabrielbmoro.moviedb.desingsystem.images.FiveStars
import com.gabrielbmoro.moviedb.desingsystem.images.MovieImage
import com.gabrielbmoro.moviedb.desingsystem.loaders.BubbleLoader
import com.gabrielbmoro.moviedb.desingsystem.toolbars.AppToolbarTitle
import com.gabrielbmoro.moviedb.desingsystem.typography.SectionBody
import com.gabrielbmoro.moviedb.desingsystem.typography.SectionTitle
import com.gabrielbmoro.moviedb.platform.LocalNavController
import com.gabrielbmoro.moviedb.platform.media.VideoPlayer
import moviedbapp.feature_tvshowdetails.generated.resources.Res
import moviedbapp.feature_tvshowdetails.generated.resources.created_by
import moviedbapp.feature_tvshowdetails.generated.resources.episodes
import moviedbapp.feature_tvshowdetails.generated.resources.first_air_date
import moviedbapp.feature_tvshowdetails.generated.resources.homepage
import moviedbapp.feature_tvshowdetails.generated.resources.language
import moviedbapp.feature_tvshowdetails.generated.resources.last_air_date
import moviedbapp.feature_tvshowdetails.generated.resources.networks
import moviedbapp.feature_tvshowdetails.generated.resources.overview
import moviedbapp.feature_tvshowdetails.generated.resources.popularity
import moviedbapp.feature_tvshowdetails.generated.resources.poster
import moviedbapp.feature_tvshowdetails.generated.resources.production_companies
import moviedbapp.feature_tvshowdetails.generated.resources.retry
import moviedbapp.feature_tvshowdetails.generated.resources.seasons
import moviedbapp.feature_tvshowdetails.generated.resources.status
import moviedbapp.feature_tvshowdetails.generated.resources.tagline
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TvShowDetailsScreen(tvShowId: Long) {
    val scrollState = rememberScrollState()
    val viewModel = koinViewModel<TvShowDetailsViewModel>()
    val uiState = viewModel.uiState.collectAsState()
    val navigator = LocalNavController.current

    val atTop by remember {
        derivedStateOf {
            scrollState.value == 0
        }
    }

    TvShowDetailsScreenMain(
        atTop = atTop,
        uiState = uiState.value,
        scrollState = scrollState,
        onBackEvent = navigator::popBackStack,
    )

    LaunchedEffect(tvShowId) {
        viewModel.executeIntent(
            TvShowDetailsUserIntent.LoadTvShowDetails(
                tvShowId = tvShowId,
            ),
        )
    }
}

@Composable
private fun TvShowDetailsScreenMain(
    atTop: Boolean,
    uiState: TvShowDetailsUIState,
    scrollState: ScrollState,
    onBackEvent: (() -> Unit),
) {
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = atTop,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                AppToolbarTitle(
                    title = uiState.tvShowName,
                    backEvent = onBackEvent,
                )
            }
        },
    ) {
        val modifier =
            Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()

        when {
            uiState.isLoading -> TvShowDetailsScreenLoading(modifier)

            uiState.errorMessage != null -> TvShowDetailsScreenError(
                modifier = modifier,
                errorMessage = uiState.errorMessage,
                onRetry = {},
            )

            else -> {
                TvShowDetailsScreenContent(
                    uiState = uiState,
                    modifier =
                    Modifier
                        .then(modifier)
                        .verticalScroll(scrollState),
                )
            }
        }
    }
}

@Composable
private fun TvShowDetailsScreenError(
    modifier: Modifier,
    errorMessage: String?,
    onRetry: (() -> Unit)?,
) {
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ErrorMessage(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            onRetry?.let {
                Button(onClick = it) {
                    Text(stringResource(Res.string.retry))
                }
            }
        }
    }
}

@Composable
private fun TvShowDetailsScreenLoading(modifier: Modifier) {
    Box(modifier = modifier) {
        BubbleLoader(
            color = MaterialTheme.colorScheme.primary,
            modifier =
            Modifier
                .align(Alignment.Center),
        )
    }
}

@Composable
private fun TvShowDetailsScreenContent(
    uiState: TvShowDetailsUIState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier =
            Modifier
                .height(280.dp)
                .fillMaxWidth(),
        ) {
            when {
                uiState.showVideo && uiState.videoId != null -> {
                    VideoPlayer(
                        videoId = uiState.videoId,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxSize(),
                    )
                }

                else -> {
                    MovieImage(
                        imageUrl = uiState.imageUrl,
                        contentScale = ContentScale.Fit,
                        modifier =
                        Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxSize(),
                        contentDescription = stringResource(Res.string.poster),
                    )
                }
            }
        }

        FiveStars(
            votes = uiState.votesAverage,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        if (uiState.genres.isNotEmpty()) {
            GenresCard(
                genres = uiState.genres,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        SectionTitle(
            title = stringResource(Res.string.overview),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        SectionBody(
            body = uiState.overview,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        SectionTitle(
            title = stringResource(Res.string.first_air_date),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        SectionBody(
            body = uiState.firstAirDate ?: "",
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        if (uiState.lastAirDate != null) {
            SectionTitle(
                title = stringResource(Res.string.last_air_date),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            SectionBody(
                body = uiState.lastAirDate,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        if (uiState.status != null) {
            SectionTitle(
                title = stringResource(Res.string.status),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            SectionBody(
                body = uiState.status,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        SectionTitle(
            title = stringResource(Res.string.seasons),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        SectionBody(
            body = uiState.numberOfSeasons.toString(),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        SectionTitle(
            title = stringResource(Res.string.episodes),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        SectionBody(
            body = uiState.numberOfEpisodes.toString(),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        SectionTitle(
            title = stringResource(Res.string.popularity),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        SectionBody(
            body = uiState.popularity.toString(),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        SectionTitle(
            title = stringResource(Res.string.language),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        SectionBody(
            body = uiState.language,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        if (uiState.tagline != null) {
            SectionTitle(
                title = stringResource(Res.string.tagline),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            SectionBody(
                body = uiState.tagline,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        if (uiState.networks.isNotEmpty()) {
            SectionTitle(
                title = stringResource(Res.string.networks),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            SectionBody(
                body = uiState.networks.joinToString(", "),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        if (uiState.createdBy.isNotEmpty()) {
            SectionTitle(
                title = stringResource(Res.string.created_by),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            SectionBody(
                body = uiState.createdBy.joinToString(", "),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        if (uiState.productionCompanies.isNotEmpty()) {
            SectionTitle(
                title = stringResource(Res.string.production_companies),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            SectionBody(
                body = uiState.productionCompanies.joinToString(", "),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        if (uiState.homepage != null) {
            SectionTitle(
                title = stringResource(Res.string.homepage),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            TextUrl(
                url = uiState.homepage,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        Spacer(
            modifier =
            Modifier
                .height(240.dp)
                .padding(horizontal = 16.dp),
        )
    }
}
