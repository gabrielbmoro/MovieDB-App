package com.gabrielbmoro.programmingchallenge.ui.screens

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.ui.common.theme.MovieDBAppTheme
import com.gabrielbmoro.programmingchallenge.presentation.settings.SettingsActivity
import com.gabrielbmoro.programmingchallenge.presentation.util.setThemeAccordingToThePreferences
import com.gabrielbmoro.programmingchallenge.ui.common.navigation.MovieDBNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        inflateMenuIfDarkThemeIsUnavailable(menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun inflateMenuIfDarkThemeIsUnavailable(menu: Menu?) {
        val isDarkThemeUnavailable = (
                Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
                )
        if (isDarkThemeUnavailable) {
            menuInflater.inflate(R.menu.main_activity_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            SettingsActivity.startActivity(this)
        }
        return super.onOptionsItemSelected(item)
    }

    @Composable
    private fun MainScreen(navController: NavHostController) {
        MovieDBNavHost(navController)
    }
}