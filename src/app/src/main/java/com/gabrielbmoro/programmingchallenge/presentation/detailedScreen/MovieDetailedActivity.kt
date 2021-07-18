package com.gabrielbmoro.programmingchallenge.presentation.detailedScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.layout.ContentScale
import androidx.core.app.ActivityOptionsCompat
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.databinding.ActivityMovieDetailedBinding
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.Favorite
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.FiveStars
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.MovieDetailDescription
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.MovieImage
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.theme.MovieDBAppTheme
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie
import com.gabrielbmoro.programmingchallenge.presentation.util.setImagePath
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class MovieDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailedBinding
    private val viewModel: MovieDetailedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie: Movie
        try {
            movie = intent.getParcelableExtra(MOVIE_INTENT_KEY) as? Movie
                ?: throw IllegalArgumentException(
                    "${MovieDetailedActivity::class.java.simpleName} requires arg $MOVIE_INTENT_KEY"
                )
            viewModel.setup(movie)

            binding = ActivityMovieDetailedBinding.inflate(layoutInflater).apply {
                composeMovieDetailed.setContent {
                    MovieDBAppTheme() {
                        MovieDetailDescription(
                            overview = movie.overview ?: "",
                            originalLanguage = movie.originalLanguage ?: "",
                            popularity = movie.popularity ?: 0f,
                            title = movie.title ?: ""
                        )
                    }
                }
            }

            setContentView(binding.root)

            setView(viewModel.movie)

            viewModel.onFavoriteMovieEvent.observe(
                this@MovieDetailedActivity
            ) {
                setFavoriteView(viewModel.movie)
            }
        } catch (illegalArgumentException: IllegalArgumentException) {
            Timber.e(illegalArgumentException)
            finish()
        }
    }

    private fun setView(movie: Movie) {
        supportActionBar?.title = movie.title

        movie.posterPath?.let { imagePath ->
            binding.composeBackdrop.setContent {
                MovieImage(imageUrl = imagePath, ContentScale.FillWidth)
            }
        }

        val votesAvg = movie.votesAverage ?: 0f
        binding.composeFiveStars.setContent {
            FiveStars(votes = votesAvg)
        }

        setFavoriteView(movie)
    }

    private fun setFavoriteView(movie: Movie) {
        binding.composeFavoriteButton.setContent {
            Favorite(isFavorite = movie.isFavorite) {
                viewModel.isToFavoriteOrUnFavorite(!movie.isFavorite)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> supportFinishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val MOVIE_INTENT_KEY = "movie key"

        /**
         * About animation
         * Reference: https://guides.codepath.com/android/shared-element-activity-transition
         */
        fun startActivity(context: Activity, movie: Movie, ivImageShared: View) {
            context.startActivity(
                Intent(context, MovieDetailedActivity::class.java).apply {
                    putExtra(MOVIE_INTENT_KEY, movie)
                },
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context,
                    ivImageShared,
                    context.resources.getString(R.string.transition_name)
                ).toBundle()
            )
        }
    }
}