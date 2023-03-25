package com.gabrielbmoro.programmingchallenge.ui.screens.details

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gabrielbmoro.programmingchallenge.core.di.ConfigVariables
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.Favorite
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.FiveStars
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.MovieDetailDescription
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.MovieImage
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.ui.common.widgets.AppToolbar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavController,
    movie: Movie,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val uiState = remember { viewModel.uiState }

    val atTop = scrollState.value == 0

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = atTop,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AppToolbar(
                    title = movie.title,
                    backEvent = {
                        navController.navigateUp()
                    }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = it.calculateTopPadding())
        ) {
            MovieImage(
                imageUrl = movie.backdropImageUrl.let {
                    "${ConfigVariables.SMALL_SIZE_IMAGE_ADDRESS}${movie.backdropImageUrl}"
                },
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            ) {
                Favorite(
                    isFavorite = uiState.value.isFavorite,
                    modifier = Modifier
                        .size(56.dp)
                        .padding(vertical = 8.dp)
                        .align(CenterStart)
                ) {
                    viewModel.isToFavoriteOrUnFavorite(!movie.isFavorite, movie)
                }
                FiveStars(
                    votes = movie.votesAverage,
                    modifier = Modifier
                        .heightIn(max = 56.dp)
                        .align(CenterEnd)
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            )

            MovieDetailDescription(
                overview = movie.overview,
                originalLanguage = movie.language,
                popularity = movie.popularity,
                modifier = Modifier.padding(bottom = 24.dp).heightIn(min = 500.dp)
            )
        }
    }

    LaunchedEffect(
        key1 = Unit,
        block = { viewModel.checkIfMovieIsFavorite(movie.title) }
    )
}