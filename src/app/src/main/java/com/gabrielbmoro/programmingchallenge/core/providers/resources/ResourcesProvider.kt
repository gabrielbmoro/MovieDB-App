package com.gabrielbmoro.programmingchallenge.core.providers.resources

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

interface ResourcesProvider {
    fun getArray(@ArrayRes arrayRes: Int): Array<String>
    fun getString(@StringRes stringRes: Int): String
}