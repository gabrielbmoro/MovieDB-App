package com.gabrielbmoro.moviedb.core.ui

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import com.google.android.material.elevation.SurfaceColors
import timber.log.Timber

fun <T : Parcelable> Bundle.parcelableOf(key: String, type: Class<T>): T? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelable(key, type)
        } else {
            getParcelable(key)
        }
    } catch (exception: Exception) {
        Timber.e(exception)
        null
    }
}

fun Activity.syncTopBarsColors() {
    val color = SurfaceColors.SURFACE_2.getColor(this)
    window.statusBarColor = color // Set color of system statusBar same as ActionBar
    window.navigationBarColor =
        color // Set color of system navigationBar same as BottomNavigationView
}