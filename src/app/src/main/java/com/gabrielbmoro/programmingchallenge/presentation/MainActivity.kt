package com.gabrielbmoro.programmingchallenge.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.BottomNavigationBar
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.Navigation
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.NavigationArgument
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.theme.MovieDBAppTheme
import com.gabrielbmoro.programmingchallenge.presentation.settings.SettingsActivity
import com.gabrielbmoro.programmingchallenge.presentation.util.setThemeAccordingToThePreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setThemeAccordingToThePreferences()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            SettingsActivity.startActivity(this)
        }
        return super.onOptionsItemSelected(item)
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
                    requestMore = { viewModel.requestMoreTopRatedMovies() },
                    loadingState = viewModel.loading.observeAsState(),
                    swipeRefreshState = viewModel.swipeRefreshLiveData,
                    onRefresh = { viewModel.refreshTopRatedMovies() }
                ),
                popularMoviesArgs = NavigationArgument(
                    moviesState = viewModel.popularMovies.observeAsState(),
                    requestMore = { viewModel.requestMorePopularMovies() },
                    loadingState = viewModel.loading.observeAsState(),
                    swipeRefreshState = viewModel.swipeRefreshLiveData,
                    onRefresh = { viewModel.refreshPopularMovies() }
                ),
                favoriteMoviesArgs = NavigationArgument(
                    moviesState = viewModel.favoriteMovies.observeAsState(),
                    requestMore = { viewModel.requestMoreFavoriteMovies() },
                    loadingState = viewModel.loading.observeAsState(),
                    swipeRefreshState = viewModel.swipeRefreshLiveData,
                    onRefresh = { viewModel.refreshFavoriteMovies() }
                )
            )
        }
    }
}