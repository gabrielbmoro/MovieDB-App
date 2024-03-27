package com.gabrielbmoro.moviedb.desingsystem.buttons

import `MovieDB-Android`.resources.MR
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource

private val deleteCardContainerColor = Color(0xFFFFDAD4L)
private val deleteCardTextColor = Color(0xFF851300L)

@Composable
fun DeleteButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = deleteCardContainerColor
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                tint = Color.Red,
                contentDescription = null,
            )

            Text(
                style = MaterialTheme.typography.titleSmall,
                color = deleteCardTextColor,
                text = stringResource(MR.strings.delete),
            )
        }
    }
}