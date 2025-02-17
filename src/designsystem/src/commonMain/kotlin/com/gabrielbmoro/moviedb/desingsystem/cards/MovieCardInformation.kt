package com.gabrielbmoro.moviedb.desingsystem.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabrielbmoro.moviedb.desingsystem.images.FiveStars

@Composable
fun MovieCardInformation(
    title: String,
    description: String,
    votes: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )

        FiveStars(
            votes = votes,
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall.copy(
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp
            ),
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )
    }
}
