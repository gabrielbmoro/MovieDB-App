package com.gabrielbmoro.programmingchallenge.ui.common.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.gabrielbmoro.programmingchallenge.core.parcelableOf
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.google.gson.Gson

class NavMovieType : NavType<Movie>(false) {
    override fun get(bundle: Bundle, key: String): Movie {
        return bundle.parcelableOf(key, Movie::class.java)!!
    }

    override fun parseValue(value: String): Movie {
        return Gson().fromJson(value, Movie::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Movie) {
        bundle.putParcelable(key, value)
    }
}
