@file:Suppress("MagicNumber")

package com.gabrielbmoro.moviedb.desingsystem.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import moviedbapp.designsystem.generated.resources.Res
import moviedbapp.designsystem.generated.resources.alt_sad_emoji
import moviedbapp.designsystem.generated.resources.ic_sad_emoji
import moviedbapp.designsystem.generated.resources.retry
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorScreen(
    message: String,
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_sad_emoji),
            contentDescription = stringResource(Res.string.alt_sad_emoji),
            modifier = Modifier.size(72.dp),
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
        )

        onRetry?.let {
            Button(onClick = onRetry) {
                Text(stringResource(Res.string.retry))
            }
        }
    }
}
