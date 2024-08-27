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
import com.gabrielbmoro.moviedb.SharedRes
import dev.icerock.moko.resources.compose.stringResource

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
                        FilterType.NowPlaying -> stringResource(SharedRes.strings.now_playing)
                        FilterType.TopRated -> stringResource(SharedRes.strings.top_rated)
                        FilterType.Popular -> stringResource(SharedRes.strings.popular)
                        FilterType.UpComing -> stringResource(SharedRes.strings.upcoming)
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

enum class FilterType {
    NowPlaying, TopRated, Popular, UpComing
}

data class FilterMenuItem(
    val selected: Boolean,
    val type: FilterType,
)