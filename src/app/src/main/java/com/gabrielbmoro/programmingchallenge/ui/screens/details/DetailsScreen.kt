package com.gabrielbmoro.programmingchallenge.ui.screens.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
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
            MovieImage(
                imageUrl = movie.imageUrl.let {
                    "${ConfigVariables.SMALL_SIZE_IMAGE_ADDRESS}${movie.imageUrl}"
                },
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .heightIn(max = 420.dp)
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
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }

    LaunchedEffect(
        key1 = Unit,
        block = { viewModel.checkIfMovieIsFavorite(movie.title) }
    )
}