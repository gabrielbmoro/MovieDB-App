package com.gabrielbmoro.moviedb.desingsystem.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.SharedRes
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(SharedRes.images.ic_sad_emoji),
            contentDescription = stringResource(SharedRes.strings.alt_sad_emoji)
        )
        Text(
            text = stringResource(SharedRes.strings.empty_view_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier =
            Modifier
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}
