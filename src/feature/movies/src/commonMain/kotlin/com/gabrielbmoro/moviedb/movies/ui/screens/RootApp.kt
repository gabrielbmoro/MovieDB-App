package com.gabrielbmoro.moviedb.movies.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.gabrielbmoro.moviedb.movies.ext.toScreenStack
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreen
import dev.theolm.rinku.compose.ext.DeepLinkListener

@Composable
fun RootApp() {
    var screenStack by remember { mutableStateOf<List<Screen>>(listOf(MoviesScreen())) }

    DeepLinkListener {
        screenStack = it.toScreenStack()
    }

    Navigator(screen = MoviesScreen()) {
        CurrentScreen()
        remember(screenStack) {
            it.replaceAll(screenStack)
        }
    }
}
