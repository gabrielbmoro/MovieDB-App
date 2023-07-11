package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.gabrielbmoro.moviedb.R
import com.gabrielbmoro.moviedb.ui.common.theme.ThemePreviews

@Composable
fun PlayButton(
    isLoading: Boolean,
    onClick: (() -> Unit),
    modifier: Modifier = Modifier,
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = modifier,
            color = Color.White
        )
    } else {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            ),
            onClick = onClick,
            modifier = modifier
        ) {
            Icon(
                painterResource(id = R.drawable.ic_play),
                null
            )
        }
    }
}

@ThemePreviews
@Composable
fun PlayButtonPreview() {
    PlayButton(onClick = {}, isLoading = false)
}

@ThemePreviews
@Composable
fun PlayButtonLoadingPreview() {
    PlayButton(onClick = {}, isLoading = true)
}