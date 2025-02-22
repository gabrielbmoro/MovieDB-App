package com.gabrielbmoro.moviedb.movies.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FilterMenu(
    menuItems: List<FilterMenuItem>,
    lazyListState: LazyListState,
    onClick: (FilterMenuItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        state = lazyListState,
    ) {
        items(menuItems) { menuItem ->
            FilterChip(
                selected = menuItem.selected,
                label = {
                    Text(stringResource(menuItem.toStringResource()))
                },
                onClick = {
                    onClick(menuItem)
                }
            )
        }
    }
}

private fun FilterMenuItem.toStringResource(): StringResource = when (type) {
    FilterType.NowPlaying -> Res.string.now_playing
    FilterType.TopRated -> Res.string.top_rated
    FilterType.Popular -> Res.string.popular
    FilterType.UpComing -> Res.string.upcoming
}
