package com.gabrielbmoro.moviedb.search.ui.widgets

import `MovieDB-Android`.resources.MR
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

private const val SEARCH_DEBOUNCE_DELAY_IN_MS = 600L

@OptIn(FlowPreview::class)
@Composable
fun SearchInputText(
    currentValue: TextFieldValue,
    onQueryChanged: ((TextFieldValue) -> Unit),
    onSearchBy: (TextFieldValue) -> Unit,
    onClearText: (() -> Unit),
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    val searchFlow = remember {
        MutableSharedFlow<TextFieldValue>()
    }
    val coroutineScope = rememberCoroutineScope()

    val trailingIcon: @Composable (() -> Unit)? = if (currentValue.text != "") {
        {
            IconButton(
                onClick = onClearText,
                modifier = Modifier
                    .size(48.dp)
            ) {
                Image(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(MR.strings.cancel),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    } else {
        null
    }

    TextField(
        value = currentValue,
        textStyle = MaterialTheme.typography.titleMedium,
        onValueChange = {
            onQueryChanged(it)

            coroutineScope.launch {
                searchFlow.emit(it)
            }
        },
        placeholder = {
            Text(
                stringResource(MR.strings.search_movie_placeholder),
                style = MaterialTheme.typography.titleMedium
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        trailingIcon = trailingIcon,
        maxLines = 1,
        modifier = modifier.focusRequester(focusRequester)
    )

    LaunchedEffect(
        key1 = Unit,
        block = {
            searchFlow.debounce(SEARCH_DEBOUNCE_DELAY_IN_MS).collect { query ->
                onSearchBy(query)
            }
        },
    )
}