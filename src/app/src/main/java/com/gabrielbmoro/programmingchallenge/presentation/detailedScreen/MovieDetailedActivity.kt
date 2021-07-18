package com.gabrielbmoro.programmingchallenge.presentation.detailedScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.Favorite
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.FiveStars
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.MovieDetailDescription
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.MovieImage
import com.gabrielbmoro.programmingchallenge.presentation.components.compose.theme.MovieDBAppTheme
import com.gabrielbmoro.programmingchallenge.repository.entities.Movie

import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class MovieDetailedActivity : AppCompatActivity() {

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

            setContent {
                val scrollState = rememberScrollState()
                val favoriteState = viewModel.onFavoriteEvent.observeAsState(movie.isFavorite)

                MovieDBAppTheme() {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(0.dp)
                    ) {
                        TopAppBar {
                            title = movie.title ?: ""
                        }

                        Box(Modifier.height(320.dp)) {
                            Surface {
                                MovieImage(
                                    imageUrl = movie.posterPath,
                                    ContentScale.FillWidth
                                )
                            }
                            Favorite(
                                isFavorite = favoriteState,
                                modifier = Modifier.align(Alignment.BottomStart),
                            ) {
                                viewModel.isToFavoriteOrUnFavorite(!movie.isFavorite)
                            }
                            FiveStars(
                                votes = movie.votesAverage ?: 0f,
                                modifier = Modifier.align(Alignment.BottomEnd)
                            )
                        }
                        MovieDetailDescription(
                            overview = movie.overview ?: "",
                            originalLanguage = movie.originalLanguage ?: "",
                            popularity = movie.popularity ?: 0f,
                            title = movie.title ?: ""
                        )
                    }
                }
            }
        } catch (illegalArgumentException: IllegalArgumentException) {
            Timber.e(illegalArgumentException)
            finish()
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