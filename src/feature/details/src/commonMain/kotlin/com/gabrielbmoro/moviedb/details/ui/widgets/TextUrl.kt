package com.gabrielbmoro.moviedb.details.ui.widgets

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun TextUrl(
    url: String,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        style =
        MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.tertiary,
            fontStyle = FontStyle.Italic,
            textDecoration = TextDecoration.Underline
        ),
        text =
        buildAnnotatedString {
            append(url)
        },
        onClick = {
            uriHandler.openUri(url)
        }
    )
}
