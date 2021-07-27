package com.gabrielbmoro.programmingchallenge.presentation.detailedScreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
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
                MovieDBAppTheme() {
                    MovieDetailedScreen(movie)
                }
            }
        } catch (illegalArgumentException: IllegalArgumentException) {
            Timber.e(illegalArgumentException)
            finish()
        }
    }

    @Composable
    private fun MovieDetailedScreen(movie: Movie) {
        Scaffold(
            topBar = {
                TopAppBar(modifier = Modifier.height(0.dp)) {
                    title = movie.title ?: ""
                }
            },
            content = {
                MovieDetailedContent()
            }
        )
    }

    @Composable
    fun MovieDetailedContent() {
        val scrollState = rememberScrollState()
        val favoriteState = viewModel.onFavoriteEvent.observeAsState(viewModel.movie.isFavorite)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier.height(
                    420.dp
                )
            ) {
                MovieImage(
                    imageUrl = viewModel.movie.posterPath,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.Black.copy(alpha = 0.5f)
                        )
                )

                Favorite(
                    isFavorite = favoriteState,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .size(56.dp)
                        .padding(start = 16.dp, bottom = 16.dp)
                ) {
                    viewModel.isToFavoriteOrUnFavorite(!viewModel.movie.isFavorite)
                }
                FiveStars(
                    votes = viewModel.movie.votesAverage ?: 0f,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp)
                )
            }
            MovieDetailDescription(
                overview = viewModel.movie.overview ?: "",
                originalLanguage = viewModel.movie.originalLanguage ?: "",
                popularity = viewModel.movie.popularity ?: 0f,
                modifier = Modifier.padding(bottom = 24.dp)
            )
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

        fun newIntent(context: Context, movie: Movie): Intent {
            return Intent(context, MovieDetailedActivity::class.java).apply {
                putExtra(MOVIE_INTENT_KEY, movie)
            }
        }
    }
}