package com.gabrielbmoro.programmingchallenge.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.BottomNavigationBar
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.Navigation
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.NavigationArgument
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.theme.MovieDBAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MovieDBAppTheme() {
                MainScreen(navController)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.refreshFavoriteMovies()
    }

    @Composable
    private fun MainScreen(navController: NavHostController) {
        Scaffold(
            topBar = { },
            bottomBar = { BottomNavigationBar(navController) },
        ) {
            Navigation(
                navController = navController,
                topRatedMoviesArgs = NavigationArgument(
                    moviesState = viewModel.topRatedMovies.observeAsState(),
                    requestMore = { viewModel.requestMoreTopRatedMoviesCallback() },
                    loadingState = viewModel.loading.observeAsState()
                ),
                popularMoviesArgs = NavigationArgument(
                    moviesState = viewModel.popularMovies.observeAsState(),
                    requestMore = { viewModel.requestMorePopularMoviesCallback() },
                    loadingState = viewModel.loading.observeAsState()
                ),
                favoriteMoviesArgs = NavigationArgument(
                    moviesState = viewModel.favoriteMovies.observeAsState(),
                    requestMore = { viewModel.requestMoreFavoriteMoviesCallback() },
                    loadingState = viewModel.loading.observeAsState()
                )
            )
        }
    }
}