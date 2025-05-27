package com.gabrielbmoro.moviedb.details.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import moviedbapp.feature.feature_details.generated.resources.Res
import moviedbapp.feature.feature_details.generated.resources.error_message
import moviedbapp.feature.feature_details.generated.resources.error_title
import moviedbapp.feature.feature_details.generated.resources.ic_dino_error
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = stringResource(Res.string.error_title),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Image(
            painter = painterResource(Res.drawable.ic_dino_error),
            modifier =
            Modifier
                .fillMaxWidth(),
            contentDescription = stringResource(Res.string.error_message),
        )
    }
}
