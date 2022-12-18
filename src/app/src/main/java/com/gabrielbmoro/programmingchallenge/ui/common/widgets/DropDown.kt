package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DropDownValue<T>(
    val currentOption: T,
    val expanded: Boolean
)

@Composable
fun <T> CommonDropDown(
    modifier: Modifier = Modifier,
    options: List<T>,
    currentValue: DropDownValue<T>,
    onValueChanged: (DropDownValue<T>) -> Unit,
) {
    Box(modifier) {
        OutlinedButton(
            modifier = modifier
                .align(alignment = Alignment.CenterStart)
                .padding(vertical = 12.dp),
            border = BorderStroke(
                2.dp,
                MaterialTheme.colors.onSurface
            ),
            onClick = { onValueChanged(currentValue.copy(expanded = true)) }) {
            Text(
                text = currentValue.currentOption.toString(),
                fontSize = 12.sp,
                style = MaterialTheme.typography.caption.copy(
                    color = MaterialTheme.colors.onSurface
                )
            )
        }

        DropdownMenu(
            expanded = currentValue.expanded,
            onDismissRequest = { onValueChanged(currentValue.copy(expanded = false)) }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onValueChanged(
                            currentValue.copy(
                                currentOption = option,
                                expanded = false
                            )
                        )
                    }
                ) {
                    Text(
                        option.toString(),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
