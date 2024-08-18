package com.gabrielbmoro.moviedb.wishlist.ui.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmationDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onPositiveAction: () -> Unit
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = "Delete movie") },
            text = { Text(text = "Do you want to remove this movie?") },
            confirmButton = {
                Button(
                    onClick = onPositiveAction
                ) {
                    Text(
                        text = "Yes",
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "No"
                    )
                }
            }
        )
    }
}
