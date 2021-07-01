package com.gabrielbmoro.programmingchallenge.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.gabrielbmoro.programmingchallenge.R
import com.gabrielbmoro.programmingchallenge.databinding.ActivityMainBinding
import com.gabrielbmoro.programmingchallenge.repository.entities.MovieListType
import com.gabrielbmoro.programmingchallenge.presentation.movieList.MovieListFragment
import com.gabrielbmoro.programmingchallenge.presentation.settings.SettingsActivity
import com.gabrielbmoro.programmingchallenge.presentation.util.setThemeAccordingToThePreferences
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * This is a view that represents the three pages: top rated movies,
 * popular movies, and favorite movies.
 * @author Gabriel Moro
 * @since 2018-08-30
 */
class MainActivity : AppCompatActivity() {

    private val fragmentsList = listOf(
        MovieListFragment.newInstance(MovieListType.TopRated),
        MovieListFragment.newInstance(MovieListType.Popular),
        MovieListFragment.newInstance(MovieListType.Favorite),
    )
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMainPagerComponent.adapter = object : FragmentStateAdapter(
            supportFragmentManager, lifecycle
        ) {
            override fun getItemCount(): Int {
                return fragmentsList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentsList[position]
            }
        }

        binding.activityMainPagerComponent.currentItem = viewModel.getPage()
        binding.activityMainPagerComponent.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    when (position) {
                        MainViewModel.TOP_RATED_PAGE -> binding.activityMainBottomMenu.selectedItemId =
                            R.id.menuTopRatedMovies
                        MainViewModel.POPULAR_PAGE -> binding.activityMainBottomMenu.selectedItemId =
                            R.id.menuPopularMovies
                        MainViewModel.FAVORITE_PAGE -> binding.activityMainBottomMenu.selectedItemId =
                            R.id.menuFavoriteMovies
                    }
                    viewModel.setPage(pageIndex = position)
                }
            }
        )

        binding.activityMainBottomMenu.setOnNavigationItemSelectedListener {
            if (binding.activityMainBottomMenu.selectedItemId == it.itemId) {
                fragmentsList[binding.activityMainPagerComponent.currentItem].scrollToTop()
            }

            when (it.itemId) {
                R.id.menuTopRatedMovies -> binding.activityMainPagerComponent.setCurrentItem(
                    0,
                    false
                )
                R.id.menuPopularMovies -> binding.activityMainPagerComponent.setCurrentItem(
                    1,
                    false
                )
                R.id.menuFavoriteMovies -> binding.activityMainPagerComponent.setCurrentItem(
                    2,
                    false
                )
            }
            true
        }

        /**
         * Limit pages in memory.
         */
        binding.activityMainPagerComponent.offscreenPageLimit = 3


        supportActionBar?.title = getString(R.string.home_title)

        setThemeAccordingToThePreferences()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> {
                SettingsActivity.startActivity(this@MainActivity)
            }
            else -> {

            }
        }
        return true
    }
}