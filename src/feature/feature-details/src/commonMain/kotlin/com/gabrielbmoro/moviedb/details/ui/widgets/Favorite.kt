package com.gabrielbmoro.moviedb.details.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import moviedbapp.feature.feature_details.generated.resources.Res
import moviedbapp.feature.feature_details.generated.resources.alt_is_favorite
import moviedbapp.feature.feature_details.generated.resources.alt_is_not_favorite
import moviedbapp.feature.feature_details.generated.resources.ic_heart_border
import moviedbapp.feature.feature_details.generated.resources.ic_heart_filled
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Favorite(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    action: (() -> Unit),
) {
    Card(
        modifier =
        modifier
            .size(56.dp)
            .clip(CircleShape)
            .clickable(onClick = action),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter =
                painterResource(
                    if (!isFavorite) {
                        Res.drawable.ic_heart_border
                    } else {
                        Res.drawable.ic_heart_filled
                    },
                ),
                contentDescription =
                if (!isFavorite) {
                    stringResource(Res.string.alt_is_not_favorite)
                } else {
                    stringResource(Res.string.alt_is_favorite)
                },
                modifier = Modifier.align(Center),
                tint = Color.Red,
            )
        }
    }
}
