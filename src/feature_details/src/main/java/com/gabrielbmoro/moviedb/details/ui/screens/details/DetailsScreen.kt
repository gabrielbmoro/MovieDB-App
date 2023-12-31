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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabrielbmoro.moviedb.core.ui.widgets.AppToolbarTitle
import com.gabrielbmoro.moviedb.core.ui.widgets.BubbleLoader
import com.gabrielbmoro.moviedb.core.ui.widgets.MovieImage
import com.gabrielbmoro.moviedb.details.ui.screens.fullscreen.FullScreenActivity
import com.gabrielbmoro.moviedb.details.ui.widgets.ErrorMessage
import com.gabrielbmoro.moviedb.details.ui.widgets.GenresCard
import com.gabrielbmoro.moviedb.details.ui.widgets.MovieDetailIndicator
import com.gabrielbmoro.moviedb.details.ui.widgets.SectionBody
import com.gabrielbmoro.moviedb.details.ui.widgets.SectionTitle
import com.gabrielbmoro.moviedb.details.ui.widgets.TextUrl
import com.gabrielbmoro.moviedb.details.ui.widgets.VideoPlayer
import com.gabrielbmoro.moviedb.feature.details.R
import com.gabrielbmoro.moviedb.repository.model.Movie

@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    movie: Movie,
    onBackEvent: () -> Unit
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val atTop by remember {
        derivedStateOf {
            scrollState.value == 0
        }
    }

    DetailsScreenMain(
        atTop = atTop,
        uiState = uiState,
        scrollState = scrollState,
        onFavoriteMovie = viewModel::isToFavoriteOrUnFavorite,
        onBackEvent = onBackEvent,
        onHideVideo = viewModel::hideVideo
    )

    LaunchedEffect(
        key1 = Unit,
        block = {
            viewModel.setup(movie)
        }
    )
}

@Composable
private fun DetailsScreenMain(
    atTop: Boolean,
    uiState: DetailsUIState,
    scrollState: ScrollState,
    onFavoriteMovie: ((Boolean) -> Unit),
    onHideVideo: () -> Unit,
    onBackEvent: (() -> Unit)
) {
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = atTop,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AppToolbarTitle(
                    title = uiState.movieTitle,
                    backEvent = onBackEvent
                )
            }
        }
    ) {
        val modifier = Modifier
            .padding(top = it.calculateTopPadding())
            .fillMaxSize()

        when {
            uiState.isLoading -> DetailsScreenLoading(modifier)

            uiState.errorMessage != null -> DetailsScreenError(modifier)

            else -> {
                DetailsScreenContent(
                    uiState = uiState,
                    modifier = Modifier
                        .then(modifier)
                        .verticalScroll(scrollState),
                    onHideVideo = onHideVideo,
                    onFavoriteMovie = onFavoriteMovie
                )
            }
        }
    }
}

@Composable
private fun DetailsScreenError(modifier: Modifier) {
    Box(modifier = modifier) {
        ErrorMessage(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun DetailsScreenLoading(modifier: Modifier) {
    Box(modifier = modifier) {
        BubbleLoader(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun DetailsScreenContent(
    uiState: DetailsUIState,
    onHideVideo: (() -> Unit),
    modifier: Modifier = Modifier,
    onFavoriteMovie: (Boolean) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .height(280.dp)
                .fillMaxWidth()
        ) {
            when {
                uiState.showVideo && uiState.videoId != null -> {
                    VideoPlayer(
                        videoId = uiState.videoId,
                        onFullScreenEvent = { videoId ->
                            onHideVideo()
                            context.startActivity(
                                FullScreenActivity.launchIntent(context, videoId)
                            )
                        },
                        shouldStartMuted = true,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxSize()
                    )
                }

                else -> {
                    MovieImage(
                        imageUrl = uiState.imageUrl,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxSize(),
                        contentDescription = stringResource(id = R.string.poster)
                    )
                }
            }
        }

        MovieDetailIndicator(
            isFavorite = uiState.isFavorite,
            votesAverage = uiState.movieVotesAverage,
            onFavoriteMovie = onFavoriteMovie,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Divider(
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        uiState.genres?.let { genres ->
            GenresCard(
                genres = genres,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        SectionTitle(
            title = stringResource(id = R.string.overview),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        SectionBody(
            body = uiState.movieOverview,
            modifier = Modifier.padding(horizontal = 16.dp)
        )


        SectionTitle(
            title = stringResource(id = R.string.popularity),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        SectionBody(
            body = uiState.moviePopularity.toString(),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SectionTitle(
            title = stringResource(id = R.string.language),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        SectionBody(
            body = uiState.movieLanguage,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        uiState.tagLine?.let { tagLine ->
            SectionTitle(
                title = stringResource(id = R.string.tagline),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            SectionBody(
                body = uiState.tagLine,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        uiState.productionCompanies?.let {
            SectionTitle(
                title = stringResource(id = R.string.production_companies),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            SectionBody(
                body = uiState.productionCompanies,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        uiState.homepage?.let {
            SectionTitle(
                title = stringResource(id = R.string.homepage),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            TextUrl(
                url = it,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .height(240.dp)
                .padding(horizontal = 16.dp)
        )
    }
}
