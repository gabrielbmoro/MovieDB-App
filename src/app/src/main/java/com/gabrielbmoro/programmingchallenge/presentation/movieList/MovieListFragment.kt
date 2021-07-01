package com.gabrielbmoro.programmingchallenge.presentation.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gabrielbmoro.programmingchallenge.databinding.FragmentMoviesListBinding
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.repository.entities.convertToMovieListType
import com.gabrielbmoro.programmingchallenge.presentation.util.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMoviesListBinding
    private val viewModel: MovieListViewModel by viewModel {
        parametersOf(
            arguments?.getInt(MOVIE_TYPE_VALUE)?.convertToMovieListType()
                ?: IllegalArgumentException(
                    "$MOVIE_TYPE_VALUE is a required argument"
                )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                binding.fragmentMoviesListProgressBar.start()
            } else {
                binding.fragmentMoviesListProgressBar.stop()
            }
        })

        viewModel.moviesLiveData.observe(viewLifecycleOwner, { movies ->
            binding.fragmentMoviesListSwRefresh.show(true)
            binding.fragmentMoviesListRvList.update(movies)
        })

        viewModel.emptyStateLiveData.observe(viewLifecycleOwner, {
            binding.fragmentMoviesListEmptyState.show(true)
        })

        viewModel.errorStateLiveData.observe(viewLifecycleOwner, {
            binding.fragmentMoviesListTvError.show(true)
        })
    }

    private fun setupRecyclerView() {
        binding.fragmentMoviesListRvList.paginationSupport {
            viewModel.load()
        }
        binding.fragmentMoviesListSwRefresh.setOnRefreshListener {
            viewModel.reload()
        }
    }

    fun scrollToTop() {
        binding.fragmentMoviesListRvList.scrollToTop()
    }

    companion object {

        private const val MOVIE_TYPE_VALUE = "bundle:movie_type"

        fun newInstance(type: MovieListType): MovieListFragment {
            return MovieListFragment().apply {
                arguments = Bundle().also { bundle ->
                    bundle.putInt(MOVIE_TYPE_VALUE, type.value)
                }
            }
        }
    }
}