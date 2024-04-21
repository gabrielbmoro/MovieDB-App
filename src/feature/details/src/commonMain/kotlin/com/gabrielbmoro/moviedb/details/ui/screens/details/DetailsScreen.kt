package com.gabrielbmoro.moviedb.details.ui.screens.details

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gabrielbmoro.moviedb.desingsystem.images.MovieImage
import com.gabrielbmoro.moviedb.desingsystem.loaders.BubbleLoader
import com.gabrielbmoro.moviedb.desingsystem.toolbars.AppToolbarTitle
import com.gabrielbmoro.moviedb.details.ui.widgets.ErrorMessage
import com.gabrielbmoro.moviedb.details.ui.widgets.GenresCard
import com.gabrielbmoro.moviedb.details.ui.widgets.MovieDetailIndicator
import com.gabrielbmoro.moviedb.details.ui.widgets.SectionBody
import com.gabrielbmoro.moviedb.details.ui.widgets.SectionTitle
import com.gabrielbmoro.moviedb.details.ui.widgets.TextUrl
import com.gabrielbmoro.moviedb.details.ui.widgets.VideoPlayer
import dev.icerock.moko.resources.compose.stringResource
import org.koin.core.parameter.parametersOf
import com.gabrielbmoro.moviedb.SharedRes

class DetailsScreen(private val movieId: Long) : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<DetailsScreenScreenModel>(parameters = { parametersOf(movieId) })
        val navigator = LocalNavigator.currentOrThrow

        val scrollState = rememberScrollState()
        val uiState by viewModel.uiState.collectAsState()

        val atTop by remember {
            derivedStateOf {
                scrollState.value == 0
            }
        }

        DetailsScreenMain(
            atTop = atTop,
            uiState = uiState,
            scrollState = scrollState,
            onFavoriteMovie = {
                viewModel.accept(DetailsUserIntent.FavoriteMovie)
            },
            onBackEvent = navigator::pop,
        )
    }
}

@Composable
private fun DetailsScreenMain(
    atTop: Boolean,
    uiState: DetailsUIState,
    scrollState: ScrollState,
    onFavoriteMovie: () -> Unit,
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
                    title = uiState.movieTitle,
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
            uiState.isLoading -> DetailsScreenLoading(modifier)

            uiState.errorMessage != null -> DetailsScreenError(modifier)

            else -> {
                DetailsScreenContent(
                    uiState = uiState,
                    modifier =
                        Modifier
                            .then(modifier)
                            .verticalScroll(scrollState),
                    onFavoriteMovie = onFavoriteMovie,
                )
            }
        }
    }
}

@Composable
private fun DetailsScreenError(modifier: Modifier) {
    Box(modifier = modifier) {
        ErrorMessage(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun DetailsScreenLoading(modifier: Modifier) {
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
private fun DetailsScreenContent(
    uiState: DetailsUIState,
    modifier: Modifier = Modifier,
    onFavoriteMovie: () -> Unit,
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
                        shouldStartMuted = true,
                        modifier =
                            Modifier
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
                        contentDescription = stringResource(SharedRes.strings.poster),
                    )
                }
            }
        }

        MovieDetailIndicator(
            isFavorite = uiState.isFavorite,
            votesAverage = uiState.movieVotesAverage,
            onFavoriteMovie = onFavoriteMovie,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        if (uiState.genres != null) {
            GenresCard(
                genres = uiState.genres,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        SectionTitle(
            title = stringResource(SharedRes.strings.overview),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        SectionBody(
            body = uiState.movieOverview,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        SectionTitle(
            title = stringResource(SharedRes.strings.popularity),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        SectionBody(
            body = uiState.moviePopularity.toString(),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        SectionTitle(
            title = stringResource(SharedRes.strings.language),
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        SectionBody(
            body = uiState.movieLanguage,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        if (uiState.tagLine != null) {
            SectionTitle(
                title = stringResource(SharedRes.strings.tagline),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            SectionBody(
                body = uiState.tagLine,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        if (uiState.productionCompanies != null) {
            SectionTitle(
                title = stringResource(SharedRes.strings.production_companies),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            SectionBody(
                body = uiState.productionCompanies,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        if (uiState.homepage != null) {
            SectionTitle(
                title = stringResource(SharedRes.strings.homepage),
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
