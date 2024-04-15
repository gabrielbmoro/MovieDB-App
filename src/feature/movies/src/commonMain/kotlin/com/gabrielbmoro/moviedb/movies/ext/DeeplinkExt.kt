package com.gabrielbmoro.moviedb.movies.ext

import cafe.adriel.voyager.core.screen.Screen
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreen
import com.gabrielbmoro.moviedb.platform.navigation.NavigationDestinations
import dev.theolm.rinku.DeepLink
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform

private val DefaultStack = listOf(MoviesScreen())

fun DeepLink.toScreenStack() : List<Screen> {
    if (this.pathSegments.isEmpty()) {
        return DefaultStack
    }
    val firstSegment = this.pathSegments.firstOrNull()
    return when (firstSegment) {
        "favorite" -> {
            val wishListScreen =
                KoinPlatform.getKoin()
                    .get<Screen>(named(NavigationDestinations.WISHLIST))
            DefaultStack + wishListScreen
        }
        "search" -> {
            val query = this.parameters["query"]
            val searchScreen = KoinPlatform.getKoin().get<Screen>(
                qualifier = named(NavigationDestinations.SEARCH),
                parameters = { parametersOf(query) }
            )

            DefaultStack + searchScreen
        }
        else -> DefaultStack
    }
}
