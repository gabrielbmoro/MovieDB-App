package com.gabrielbmoro.programmingchallenge.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.BottomNavigationBar
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.Navigation
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.theme.MovieDBAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MovieDBAppTheme() {
                Scaffold(
                    topBar = { },
                    bottomBar = { BottomNavigationBar(navController) },
                ) {
                    Navigation(navController)
                }
            }
        }
    }
}