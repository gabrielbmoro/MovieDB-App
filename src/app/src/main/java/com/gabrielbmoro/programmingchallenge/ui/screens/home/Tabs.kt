package com.gabrielbmoro.programmingchallenge.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType

@Composable
fun TopRatedMoviesTab(navController: NavController, viewModel: MovieListViewModel) {
    viewModel.setup(MovieListType.TopRated)
    MovieListScreen(navController, viewModel)
}

@Composable
fun PopularMoviesTab(navController: NavController, viewModel: MovieListViewModel) {
    viewModel.setup(MovieListType.Popular)
    MovieListScreen(navController, viewModel)
}

@Composable
fun FavoriteMoviesTab(navController: NavController, viewModel: MovieListViewModel) {
    viewModel.setup(MovieListType.Favorite)
    MovieListScreen(navController, viewModel)
}