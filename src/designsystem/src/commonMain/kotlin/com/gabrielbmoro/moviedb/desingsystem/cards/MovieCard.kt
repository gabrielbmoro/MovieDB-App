@file:Suppress("LongParameterList", "LongMethod")

package com.gabrielbmoro.moviedb.desingsystem.cards

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.gabrielbmoro.moviedb.desingsystem.buttons.DeleteButton
import com.gabrielbmoro.moviedb.desingsystem.images.MovieImage
import moviedbapp.designsystem.generated.resources.Res
import moviedbapp.designsystem.generated.resources.poster
import org.jetbrains.compose.resources.stringResource

val CardViewImageHeight = 200.dp
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
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        MovieImage(
            imageUrl = imageUrl,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = CardViewImageHeight),
            contentDescription = stringResource(Res.string.poster)
        )
        MovieCardInformation(
            title = title,
            votes = votes,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 6.dp),
            description = description
        )

        if (enableDelete) {
            DeleteButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(DeleteButtonSize)
            )
        }
    }
}
