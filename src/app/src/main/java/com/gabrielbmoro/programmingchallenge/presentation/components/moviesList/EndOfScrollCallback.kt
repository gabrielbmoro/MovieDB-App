package com.gabrielbmoro.programmingchallenge.presentation.components.moviesList

import androidx.recyclerview.widget.RecyclerView

typealias EndOfScrollCallbackAction = (() -> Unit)

class EndOfScrollCallback(private val callback: EndOfScrollCallbackAction) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        (recyclerView as? MoviesRecyclerViewComponent)?.let { rv ->
            if (rv.checkIfIsAtTheEndOfScroll()) {
                callback.invoke()
            }
        }
    }
}