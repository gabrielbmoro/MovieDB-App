package com.gabrielbmoro.moviedb

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.Navigator
import com.gabrielbmoro.moviedb.desingsystem.theme.MovieDBAppTheme
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreen
import ext.syncTopBarsColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        syncTopBarsColors()

        setContent {
            DynamicColorApp {
                Navigator(screen = MoviesScreen())
            }
        }
    }
}

@Composable
fun DynamicColorApp(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val hasDynamicColorsFeature = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)

    val dynamicColorScheme =
        if (hasDynamicColorsFeature) {
            if (isSystemInDarkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        } else {
            null
        }

    MovieDBAppTheme(
        dynamicColorScheme = dynamicColorScheme,
        content = content,
    )
}
