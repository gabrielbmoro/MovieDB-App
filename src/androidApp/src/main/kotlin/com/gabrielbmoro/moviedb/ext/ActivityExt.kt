package com.gabrielbmoro.moviedb.ext

import android.app.Activity
import com.google.android.material.elevation.SurfaceColors

fun Activity.syncTopBarsColors() {
    val color = SurfaceColors.SURFACE_2.getColor(this)
    window.statusBarColor = color // Set color of system statusBar same as ActionBar
    window.navigationBarColor =
        color // Set color of system navigationBar same as BottomNavigationView
}