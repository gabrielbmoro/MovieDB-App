package com.gabrielbmoro.programmingchallenge.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType

@Composable
fun TopRatedMoviesTab(navController: NavController) {
    HomeScreen(navController, movieType = MovieListType.TopRated)
}

@Composable
fun PopularMoviesTab(navController: NavController) {
    HomeScreen(navController, movieType = MovieListType.Popular)
}

@Composable
fun FavoriteMoviesTab(navController: NavController) {
    HomeScreen(navController, movieType = MovieListType.Favorite)
}