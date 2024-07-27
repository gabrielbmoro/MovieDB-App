package com.gabrielbmoro.moviedb.platform

interface ViewModelMvi<in UserIntent : Any> {
    fun execute(intent: UserIntent)
}
