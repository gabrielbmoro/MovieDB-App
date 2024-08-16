@file:Suppress("LongParameterList", "LongMethod")

package com.gabrielbmoro.moviedb.desingsystem.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.SharedRes
import com.gabrielbmoro.moviedb.desingsystem.buttons.DeleteButton
import com.gabrielbmoro.moviedb.desingsystem.images.MovieImage
import dev.icerock.moko.resources.compose.stringResource

val CardViewImageHeight = 200.dp
val PosterCardWidth = 120.dp
val DeleteButtonSize = 48.dp

@Composable
fun MovieCard(
    imageUrl: String?,
    title: String,
    description: String,
    votes: Float,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit),
    enableDelete: Boolean = false,
    onDeleteClick: (() -> Unit) = {}
) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier.height(CardViewImageHeight),
            shape = RoundedCornerShape(12.dp),
            onClick = onClick
        ) {
            Box {
                if (enableDelete) {
                    DeleteButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.align(Alignment.BottomEnd).size(DeleteButtonSize)
                    )
                }

                Row {
                    MovieImage(
                        imageUrl = imageUrl,
                        contentScale = ContentScale.FillHeight,
                        modifier =
                        Modifier
                            .width(PosterCardWidth)
                            .fillMaxHeight(),
                        contentDescription = stringResource(SharedRes.strings.poster)
                    )
                    MovieCardInformation(
                        title = title,
                        votes = votes,
                        modifier = Modifier.fillMaxSize().padding(bottom = DeleteButtonSize),
                        description = description
                    )
                }
            }
        }
    }
}
