package com.gabrielbmoro.moviedb.platform

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }
