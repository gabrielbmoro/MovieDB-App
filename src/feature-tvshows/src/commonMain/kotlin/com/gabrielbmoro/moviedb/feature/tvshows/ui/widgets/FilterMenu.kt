package com.gabrielbmoro.moviedb.feature.tvshows.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows.FilterMenuItem
import com.gabrielbmoro.moviedb.feature.tvshows.ui.screens.tvshows.TvShowFilterType
import kotlinx.collections.immutable.ImmutableList
import moviedbapp.feature_tvshows.generated.resources.Res
import moviedbapp.feature_tvshows.generated.resources.airing_today
import moviedbapp.feature_tvshows.generated.resources.on_the_air
import moviedbapp.feature_tvshows.generated.resources.popular
import moviedbapp.feature_tvshows.generated.resources.top_rated
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FilterMenu(
    menuItems: ImmutableList<FilterMenuItem>,
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
                },
            )
        }
    }
}

private fun FilterMenuItem.toStringResource(): StringResource = when (type) {
    TvShowFilterType.Popular -> Res.string.popular
    TvShowFilterType.TopRated -> Res.string.top_rated
    TvShowFilterType.OnTheAir -> Res.string.on_the_air
    TvShowFilterType.AiringToday -> Res.string.airing_today
}
