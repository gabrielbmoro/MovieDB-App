package com.gabrielbmoro.programmingchallenge.presentation.favoriteMovieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gabrielbmoro.programmingchallenge.databinding.FragmentFavoriteMoviesListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieListFragment : Fragment(), ScrollableFragment {

    private lateinit var binding: FragmentFavoriteMoviesListBinding
    private val viewModel: FavoriteMoviesViewModel by viewModel()
    private val adapter = FavoriteMoviesListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentFavoriteMoviesListRvList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.setup()?.observe(
                viewLifecycleOwner,
            {
                adapter.submitList(it)
            }
        )
    }

    override fun scrollToTop() {
        binding.fragmentFavoriteMoviesListRvList.smoothScrollToPosition(0)
    }

}