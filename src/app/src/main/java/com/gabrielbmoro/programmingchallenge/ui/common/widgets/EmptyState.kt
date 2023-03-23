package com.gabrielbmoro.programmingchallenge.ui.common.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.programmingchallenge.R

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(modifier) {
        Image(
            painter = painterResource(R.mipmap.ic_sad_emoji),
            contentDescription = stringResource(id = R.string.alt_sad_emoji),
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.empty_view_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}