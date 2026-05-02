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
import moviedbapp.designsystem.generated.resources.network_failure
import moviedbapp.designsystem.generated.resources.please_try_again
import moviedbapp.designsystem.generated.resources.retry
import moviedbapp.designsystem.generated.resources.something_wrong_happened
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorScreen(
    errorInfo: ErrorInfo,
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_sad_emoji),
            contentDescription = stringResource(Res.string.alt_sad_emoji),
            modifier = Modifier.size(72.dp),
        )

        val message = when (errorInfo) {
            ErrorInfo.SOMETHING_WRONG_HAPPENED -> stringResource(Res.string.something_wrong_happened)
            ErrorInfo.NETWORK_ERROR -> stringResource(Res.string.network_failure)
            ErrorInfo.PLEASE_TRY_AGAIN -> stringResource(Res.string.please_try_again)
        }

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
        )

        onRetry?.let {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onRetry,
            ) {
                Text(stringResource(Res.string.retry))
            }
        }
    }
}
