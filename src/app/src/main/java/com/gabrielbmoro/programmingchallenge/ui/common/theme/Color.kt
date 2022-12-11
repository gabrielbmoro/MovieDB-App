package com.gabrielbmoro.programmingchallenge.ui.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple500 = Color(0xff6200ee)
val Blue700 = Color(0xff3700b3)
val Teal200 = Color(0xff03dac5)

@Composable
fun BottomBackgroundAndContentColors(): Pair<Color,Color> {
    return if(isSystemInDarkTheme()) Pair(Color.Black, Color.White)
    else Pair(Color.White, Color.Black)
}