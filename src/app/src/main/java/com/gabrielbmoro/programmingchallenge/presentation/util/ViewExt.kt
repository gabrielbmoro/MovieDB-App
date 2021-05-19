package com.gabrielbmoro.programmingchallenge.presentation.util

import android.view.View

fun View.show(display: Boolean) {
    visibility = if (display) View.VISIBLE else View.GONE
}