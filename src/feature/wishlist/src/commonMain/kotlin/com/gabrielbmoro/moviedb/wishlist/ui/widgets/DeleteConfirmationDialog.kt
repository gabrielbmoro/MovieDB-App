package com.gabrielbmoro.moviedb.wishlist.ui.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import moviedbapp.feature.wishlist.generated.resources.Res
import moviedbapp.feature.wishlist.generated.resources.confirm_delete_dialog_body
import moviedbapp.feature.wishlist.generated.resources.confirm_delete_dialog_negative_action
import moviedbapp.feature.wishlist.generated.resources.confirm_delete_dialog_positive_action
import moviedbapp.feature.wishlist.generated.resources.confirm_delete_dialog_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun DeleteConfirmationDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onPositiveAction: () -> Unit,
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = stringResource(Res.string.confirm_delete_dialog_title)) },
            text = { Text(text = stringResource(Res.string.confirm_delete_dialog_body)) },
            confirmButton = {
                Button(
                    onClick = onPositiveAction,
                ) {
                    Text(
                        text = stringResource(Res.string.confirm_delete_dialog_positive_action),
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.outlinedButtonColors(),
                ) {
                    Text(
                        text = stringResource(Res.string.confirm_delete_dialog_negative_action),
                    )
                }
            },
        )
    }
}
