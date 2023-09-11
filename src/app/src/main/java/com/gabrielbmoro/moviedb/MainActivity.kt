package com.gabrielbmoro.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gabrielbmoro.moviedb.core.ui.theme.MovieDBAppTheme
import com.gabrielbmoro.moviedb.navigation.MovieDBNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            MovieDBAppTheme {
                MainScreen(navController)
            }
        }
    }

    @Composable
    private fun MainScreen(navController: NavHostController) {
        MovieDBNavHost(navController)
    }
}