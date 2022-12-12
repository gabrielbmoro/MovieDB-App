package com.gabrielbmoro.programmingchallenge.ui.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gabrielbmoro.programmingchallenge.domain.model.ThemeType
import com.gabrielbmoro.programmingchallenge.ui.common.theme.MovieDBAppTheme
import com.gabrielbmoro.programmingchallenge.ui.common.navigation.MovieDBNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val viewModel: MainViewModel = hiltViewModel()

            val darkTheme = when (viewModel.themeType()) {
                ThemeType.AUTOMATIC -> isSystemInDarkTheme()
                ThemeType.NIGHT -> true
                ThemeType.LIGHT -> false
            }
            MovieDBAppTheme(darkTheme = darkTheme) {
                MainScreen(navController)
            }
        }
    }

    @Composable
    private fun MainScreen(navController: NavHostController) {
        MovieDBNavHost(navController)
    }
}