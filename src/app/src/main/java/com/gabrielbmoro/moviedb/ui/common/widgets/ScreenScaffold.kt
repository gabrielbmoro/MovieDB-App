package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ScreenScaffold(
    showTopBar: Boolean,
    appBarTitle: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    scrollToTop: (() -> Unit),
    screenContent: @Composable BoxScope.() -> Unit,
) {
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = showTopBar,
                enter = expandVertically(
                    tween(delayMillis = 200, durationMillis = 500)
                ),
                exit = shrinkVertically()
            ) {
                AppToolbar(
                    title = appBarTitle,
                    backEvent = null
                )
            }
        },
        bottomBar = {
            MovieBottomNavigationBar(
                navController,
                scrollToTop = scrollToTop
            )
        },
    ) {
        Box(
            modifier = modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp,
                )
                .fillMaxSize()
        ) {
            screenContent()
        }
    }
}