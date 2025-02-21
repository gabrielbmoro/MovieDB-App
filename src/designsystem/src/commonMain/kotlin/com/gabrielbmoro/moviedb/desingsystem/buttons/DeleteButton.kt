@file:Suppress("MagicNumber")

package com.gabrielbmoro.moviedb.desingsystem.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moviedbapp.designsystem.generated.resources.Res
import moviedbapp.designsystem.generated.resources.delete
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(Res.string.delete),
        )
    }
}
