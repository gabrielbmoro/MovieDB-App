package com.gabrielbmoro.moviedb.movies.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType
import moviedbapp.feature.movies.generated.resources.Res
import moviedbapp.feature.movies.generated.resources.now_playing
import moviedbapp.feature.movies.generated.resources.popular
import moviedbapp.feature.movies.generated.resources.top_rated
import moviedbapp.feature.movies.generated.resources.upcoming
import org.jetbrains.compose.resources.stringResource

@Composable
fun FilterMenu(
    menuItems: List<FilterMenuItem>,
    onClick: (FilterMenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            count = menuItems.size
        ) { index ->
            val menuItem = menuItems[index]
            FilterChip(
                selected = menuItem.selected,
                label = {
                    val menuTitle = when (menuItem.type) {
                        FilterType.NowPlaying -> stringResource(Res.string.now_playing)
                        FilterType.TopRated -> stringResource(Res.string.top_rated)
                        FilterType.Popular -> stringResource(Res.string.popular)
                        FilterType.UpComing -> stringResource(Res.string.upcoming)
                    }
                    Text(menuTitle)
                },
                onClick = {
                    onClick(menuItem)
                }
            )
        }
    }
}
