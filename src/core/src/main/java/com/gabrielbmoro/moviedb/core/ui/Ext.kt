package com.gabrielbmoro.moviedb.core.ui

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
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
