package com.gabrielbmoro.moviedb.core.providers.resources

import android.content.res.Resources
import androidx.annotation.ArrayRes

class AndroidResourcesProvider(
    private val resources: Resources
) : ResourcesProvider {

    override fun getArray(@ArrayRes arrayRes: Int): Array<String> {
        return resources.getStringArray(arrayRes)
    }

    override fun getString(stringRes: Int): String {
        return resources.getString(stringRes)
    }
}