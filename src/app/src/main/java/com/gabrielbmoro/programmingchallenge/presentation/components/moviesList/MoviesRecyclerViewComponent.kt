package com.gabrielbmoro.programmingchallenge.presentation.components.moviesList

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabrielbmoro.programmingchallenge.presentation.components.adapter.MoviesListAdapter

class MoviesRecyclerViewComponent @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = MoviesListAdapter()
    }

    fun paginationSupport(callback: EndOfScrollCallbackAction) {
        addOnScrollListener(EndOfScrollCallback(callback))
    }

    fun scrollToTop() = smoothScrollToPosition(FIRST_POSITION)

    fun adapterImplementation() = adapter as? MoviesListAdapter

    private fun lastAdapterIndex(): Int {
        return adapter?.itemCount?.let {
            if (it > FIRST_POSITION) {
                (it - 1)
            } else FIRST_POSITION
        } ?: FIRST_POSITION
    }

    fun checkIfIsAtTheEndOfScroll(): Boolean {
        return (layoutManager as? LinearLayoutManager)?.let { llm ->
            llm.findLastVisibleItemPosition() == lastAdapterIndex()
        } ?: false
    }

    companion object {
        private const val FIRST_POSITION = 0
    }
}
