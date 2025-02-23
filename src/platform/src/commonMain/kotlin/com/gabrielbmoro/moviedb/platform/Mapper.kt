package com.gabrielbmoro.moviedb.platform

interface Mapper<in I, out O> {
    fun map(input: I): O
}
