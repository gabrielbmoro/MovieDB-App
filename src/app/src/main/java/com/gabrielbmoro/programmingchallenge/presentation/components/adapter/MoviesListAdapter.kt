package com.gabrielbmoro.programmingchallenge.presentation.components.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.domain.model.Movie

class MoviesListAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private val elements = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie_card, parent, false))
    }

    override fun getItemCount() = elements.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        try {
            elements[position]
        } catch (indexOutOfBounds: IndexOutOfBoundsException) {
            null
        }?.let { data ->
            holder.bind(data)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    fun setup(movieDataList: List<Movie>) {
        elements.clear()
        elements.addAll(movieDataList)
        notifyDataSetChanged()
    }

    fun update(newElements: List<Movie>) {
        val previousSize = elements.size
        elements.addAll(
                newElements
        )
        notifyItemRangeInserted(previousSize, newElements.size)
    }

    companion object {
        const val VIEW_TYPE = -123
    }
}