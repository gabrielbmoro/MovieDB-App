package com.gabrielbmoro.programmingchallenge.ui.screens.details

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavController,
    movie: Movie,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val uiState = remember { viewModel.uiState }

    Scaffold(
        topBar = {
            AppToolbar(
                title = movie.title,
                backEvent = {
                    navController.navigateUp()
                },
                extraEvent = null
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier.height(
                    420.dp
                )
            ) {
                MovieImage(
                    imageUrl = movie.imageUrl.let {
                        "${ConfigVariables.SMALL_SIZE_IMAGE_ADDRESS}${movie.imageUrl}"
                    },
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.Black.copy(alpha = 0.5f)
                        )
                )

                Favorite(
                    isFavorite = uiState.value.isFavorite,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .size(56.dp)
                        .padding(start = 16.dp, bottom = 16.dp)
                ) {
                    viewModel.isToFavoriteOrUnFavorite(!movie.isFavorite, movie)
                }
                FiveStars(
                    votes = movie.votesAverage,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp)
                )
            }
            MovieDetailDescription(
                overview = movie.overview,
                originalLanguage = movie.language,
                popularity = movie.popularity,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(Modifier.height(32.dp))
        }
    }

    LaunchedEffect(
        key1 = Unit,
        block = { viewModel.checkIfMovieIsFavorite(movie.title) }
    )
}