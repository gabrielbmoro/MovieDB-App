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
import moviedb_android.design_system.generated.resources.Res
import moviedb_android.design_system.generated.resources.alt_sad_emoji
import moviedb_android.design_system.generated.resources.empty_view_title
import moviedb_android.design_system.generated.resources.ic_sad_emoji
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_sad_emoji),
            contentDescription = stringResource(Res.string.alt_sad_emoji)
        )
        Text(
            text = stringResource(Res.string.empty_view_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}