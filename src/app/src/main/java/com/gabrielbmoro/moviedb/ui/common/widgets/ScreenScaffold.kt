package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController

@Composable
fun ScreenScaffold(
    areBarsVisible: Boolean,
    appBarTitle: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    onShowBars: ((Boolean) -> Unit),
    scrollToTop: (() -> Unit),
    screenContent: @Composable BoxScope.() -> Unit,
) {
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = areBarsVisible,
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
            AnimatedVisibility(
                visible = areBarsVisible,
                enter = fadeIn(
                    tween(delayMillis = 200, durationMillis = 500)
                ),
                exit = fadeOut()
            ) {
                MovieBottomNavigationBar(
                    navController,
                    scrollToTop = scrollToTop
                )
            }
        },
    ) {
        Box(
            modifier = modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .fillMaxSize()
                .nestedScroll(
                    object : NestedScrollConnection {
                        override fun onPostScroll(
                            consumed: Offset,
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            onShowBars(consumed.y == 0f)
                            return super.onPostScroll(consumed, available, source)
                        }
                    },
                )
        ) {
            screenContent()
        }
    }
}