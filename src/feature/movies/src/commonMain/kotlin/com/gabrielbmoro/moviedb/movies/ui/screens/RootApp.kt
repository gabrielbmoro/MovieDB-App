package com.gabrielbmoro.moviedb.movies.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreen
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import dev.theolm.rinku.DeepLink
import dev.theolm.rinku.compose.ext.DeepLinkListener
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform

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

fun DeepLink.toScreenStack() : List<Screen> {
    if (this.pathSegments.isEmpty()) {
        return listOf(MoviesScreen())
    }
    val firstSegment = this.pathSegments.first()
    return when (firstSegment) {
        "favorite" -> {
            val wishListScreen =
                KoinPlatform.getKoin()
                    .get<Screen>(named(NavigationDestinations.WISHLIST))
            listOf(MoviesScreen(), wishListScreen)
        }
        "search" -> {
            val searchScreen = KoinPlatform.getKoin().get<Screen>(named(NavigationDestinations.SEARCH))
            listOf(MoviesScreen(), searchScreen)
        }
        else -> listOf(MoviesScreen())
    }
}
