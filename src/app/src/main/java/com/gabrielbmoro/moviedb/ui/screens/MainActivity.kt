package com.gabrielbmoro.moviedb.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gabrielbmoro.moviedb.ui.common.theme.MovieDBAppTheme
import com.gabrielbmoro.moviedb.ui.common.navigation.MovieDBNavHost

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