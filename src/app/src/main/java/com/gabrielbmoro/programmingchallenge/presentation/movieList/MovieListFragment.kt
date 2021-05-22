package com.gabrielbmoro.programmingchallenge.presentation.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gabrielbmoro.programmingchallenge.databinding.FragmentMoviesListBinding
import com.gabrielbmoro.programmingchallenge.presentation.ViewModelResult
import com.gabrielbmoro.programmingchallenge.domain.model.MovieListType
import com.gabrielbmoro.programmingchallenge.domain.model.convertToMovieListType
import com.gabrielbmoro.programmingchallenge.presentation.favoriteMovieList.ScrollableFragment
import com.gabrielbmoro.programmingchallenge.presentation.util.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(), ScrollableFragment {

    private lateinit var binding: FragmentMoviesListBinding
    private val viewModel: MovieListViewModel by viewModel()

    private val observer = Observer<ViewModelResult> { result ->
        binding.fragmentMoviesListSwRefresh.isRefreshing = false
        when (result) {
            is ViewModelResult.Error -> {
                binding.fragmentMoviesListTvError.show(true)
                binding.fragmentMoviesListProgressBar.stop()
                binding.fragmentMoviesListSwRefresh.show(false)
            }
            is ViewModelResult.Loading -> {
                binding.fragmentMoviesListTvError.show(false)
                binding.fragmentMoviesListProgressBar.start()
                binding.fragmentMoviesListSwRefresh.show(false)
            }
            is ViewModelResult.Success -> {
                binding.fragmentMoviesListRvList.adapterImplementation()?.setup(viewModel.movies())
                showTheRefreshLayout()
            }
            is ViewModelResult.Updated -> {
                binding.fragmentMoviesListRvList.adapterImplementation()?.update(viewModel.newPart())
                showTheRefreshLayout()
            }
        }
    }

    private fun showTheRefreshLayout() {
        binding.fragmentMoviesListSwRefresh.show(true)
        binding.fragmentMoviesListProgressBar.stop()
        binding.fragmentMoviesListTvError.show(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMovieTypeFromIntent()?.let { type ->
            setupViewModel(type)
            setupRecyclerView(type)
        }
    }

    private fun getMovieTypeFromIntent(): MovieListType? {
        return arguments?.getInt(MOVIE_TYPE_VALUE)?.convertToMovieListType()
    }

    private fun setupViewModel(type: MovieListType) {
        viewModel.onMoviesListReceived.observe(viewLifecycleOwner, observer)
        viewModel.setup(type)
    }

    private fun setupRecyclerView(type: MovieListType) {
        if (type == MovieListType.TopRated || type == MovieListType.Popular) {
            binding.fragmentMoviesListRvList.paginationSupport {
                viewModel.requestMore()
            }
        }
        binding.fragmentMoviesListSwRefresh.setOnRefreshListener {
            viewModel.reload()
        }
    }

    override fun scrollToTop() {
        binding.fragmentMoviesListRvList.scrollToTop()
    }

    companion object {

        private const val MOVIE_TYPE_VALUE = "aspd"

        fun newInstance(type: MovieListType): MovieListFragment {
            return MovieListFragment().apply {
                arguments = Bundle().also { bundle ->
                    bundle.putInt(MOVIE_TYPE_VALUE, type.value)
                }
            }
        }

    }
}