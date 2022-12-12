package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Alert(
    title: String,
    message: String,
    positiveText: String,
    positiveAct: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
            )
        },
        text = {
            Text(
                text = message,
            )
        },
        buttons = {
            Row(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = positiveAct,
                ) {
                    Text(
                        text = positiveText,
                    )
                }
            }
        }
    )
}

